package ch.heigvd.amt.services;

import ch.heigvd.amt.entities.Artist;
import ch.heigvd.amt.entities.Artwork;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class DiscoveryService {

    @Inject
    EntityManager entityManager;

    public List<Artist> findAllArtists() {
        return entityManager.createQuery("SELECT a FROM Artist a", Artist.class).getResultList();
    }

    public List<Artist> findAllArtistsByNumberOfArtworksDesc() {
        return entityManager.createQuery("SELECT a FROM Artist a ORDER BY SIZE(a.artworks) DESC", Artist.class).getResultList();
    }

    public String findArtistImage(Artist artist) {
        var qid = artist.getWikiLink();
        if (qid == null || qid.isBlank()) {
            return null;
        }

        String sparqlEndpoint = "https://query.wikidata.org/sparql";
        SPARQLRepository repo = new SPARQLRepository(sparqlEndpoint);

        String userAgent = "Wikidata RDF4J Java Example/0.1 (https://query.wikidata.org/)";
        repo.setAdditionalHttpHeaders(Collections.singletonMap("User-Agent", userAgent));

        String query = String.format("SELECT ?image WHERE { wd:%s wdt:P18 ?image. }", qid);
        try(var result = repo.getConnection().prepareTupleQuery(query).evaluate()) {
            var first = result.stream().findFirst();
            return first.map(bindings -> bindings.getValue("image").stringValue()).orElse(null);
        }
    }


    public Artist findArtistById(Long id) {
        return entityManager.createQuery("SELECT a FROM Artist a WHERE a.id = :id", Artist.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Artwork findArtworkById(Long artworkId) {
        return entityManager.createQuery("SELECT a FROM Artwork a WHERE a.id = :id", Artwork.class)
                .setParameter("id", artworkId)
                .getSingleResult();
    }

    public List<Artwork> findSimilarArtwork(Long id) {
        return entityManager.createNativeQuery("""
                                SELECT
                                    artwork.*,
                                    ts_rank(to_tsvector(artwork.title), query) as rank
                                FROM
                                    artwork,
                                    to_tsquery((SELECT regexp_replace(regexp_replace(title, '[^a-zA-Z0-9 ]', '', 'g'), '\\s+', ' | ', 'g') FROM artwork WHERE id = :id)) as query
                                WHERE
                                    to_tsvector(artwork.title) @@ query 
                                    AND artwork.thumbnailurl != ''
                                ORDER BY rank DESC LIMIT 20
                        """, Artwork.class)
                .setParameter("id", id)
                .getResultList();
    }
}
