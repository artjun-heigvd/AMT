package ch.heigvd.amt.resources;

import ch.heigvd.amt.services.DiscoveryService;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.stream.Collectors;

@Path("/discovery")
public class DiscoveryResource {

    @Inject
    DiscoveryService discoveryService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        var artists = discoveryService.findAllArtists().stream()
                .map(artist -> String.format("<li><a href=\"/discovery/artists/%d\">%s</a></li>", artist.getId(), artist.getName()))
                .collect(Collectors.joining());
        return String.format("<h1>Artists</h1><p><a href=\"/discovery/prolific\">By number of artworks</a></p><ul>%s</ul>", artists);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/prolific")
    @CacheResult(cacheName = "discovery-cache")
    public String indexByNumberOfArtwork() {

        // Question 1: This service is making a call to the database to get the artists. How can we improve its performance?
        var artists = discoveryService.findAllArtistsByNumberOfArtworksDesc().stream()
                .map(artist -> String.format("<li><a href=\"/discovery/artists/%d\">%s</a></li>", artist.getId(), artist.getName()))
                .collect(Collectors.joining());

        return String.format("<h1>Artists</h1><p><a href=\"/discovery\">By alphabetical order</a></p><ul>%s</ul>", artists);
    }

    @GET
    @Path("/artists/{id}")
    @Produces(MediaType.TEXT_HTML)
    @CacheResult(cacheName = "discovery-cache")

    public String artist(Long id) {
        var artist = discoveryService.findArtistById(id);

        // Question 2: This method is making a call to Wikidata to get the image of the artist. How can we improve its performance?
        var image = discoveryService.findArtistImage(artist);

        var imageTag = image != null ? String.format("<img src=\"%s\" />", image) : "";
        var artworks = artist.getArtworks().stream()
                .map(artwork -> String.format("<li><a href=\"/discovery/artworks/%d\">%s</a></li>", artwork.getId(), artwork.getTitle(), artwork.getTitle()))
                .collect(Collectors.joining());
        var link = artist.getWikiLink() != null && !artist.getWikiLink().isBlank() ? String.format(" (<a href=\"https://www.wikidata.org/wiki/%s\">more</a>)", artist.getWikiLink()) : "";
        return String.format("<h1>%s</h1>%s<p>%s%s</p><ul>%s</ul>", artist.getName(), imageTag, artist.getBio(), link, artworks);
    }

    @GET
    @Path("/artworks/{artworkId}")
    @Produces(MediaType.TEXT_HTML)
    @CacheResult(cacheName = "discovery-cache")

    public String artwork(Long artworkId) {
        var artwork = discoveryService.findArtworkById(artworkId);
        var artists = artwork.getArtists().stream()
                .map(a -> String.format("<li><a href=\"/discovery/artists/%d\">%s</a></li>", a.getId(), a.getName()))
                .collect(Collectors.joining());

        // Question 3 : This method is making a call to the database to get the similar artworks. How can we improve its performance?
        var similarArtworks = discoveryService.findSimilarArtwork(artworkId).stream()
                .map(a -> String.format("<a href=\"/discovery/artworks/%d\"><img alt=\"%s\" src=\"%s\" /></a>", a.getId(), a.getTitle(), a.getThumbnailUrl()))
                .collect(Collectors.joining());

        return String.format("<h1>%s</h1><ul>%s</ul><img src=\"%s\" /><h2>Similar artworks</h2>%s", artwork.getTitle(), artists, artwork.getThumbnailUrl(), similarArtworks);
    }



}
