package ch.heigvd.amt.jpa.service;

import ch.heigvd.amt.jpa.entity.Actor;
import ch.heigvd.amt.jpa.entity.Rating;
import ch.heigvd.amt.jpa.entity.Actor_;
import ch.heigvd.amt.jpa.entity.Film_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Service to get actors who played in PG-rated films. The query wanted is the following:
 * List the actors (firstname, lastname) and their number of films, for films that have PG rating, ordered by: their
 * number of films in descending order, the actor's firstname, the actor's lastname and the actor unique ID.
 * <p>
 * The service provides 4 methods to achieve the same result:
 * - actorInPGRatings_NativeSQL: using a native SQL query
 * - actorInPGRatings_JPQL: using JPQL
 * - actorInPGRatings_CriteriaString: using the Criteria API with string-based metamodel
 * - actorInPGRatings_CriteriaMetaModel: using the Criteria API with metamodel
 */
@ApplicationScoped
public class ActorsInPGService {

    @Inject
    private EntityManager em;

    /**
     * Record to represent an actor who played in PG-rated films.
     *
     * @param firstName first name of the actor
     * @param lastName  last name of the actor
     * @param nbFilms   number of films the actor played in
     */
    public record ActorInPGRating(String firstName, String lastName, Long nbFilms) {
    }

    /**
     * Get actors who played in PG-rated films using a native SQL query.
     *
     * @return list of actors who played in PG-rated films
     */
    public List<ActorInPGRating> actorInPGRatings_NativeSQL() {
        String sql = """
                    SELECT a.first_name, a.last_name, COUNT(f.film_id) AS nbFilms
                    FROM actor a
                             JOIN film_actor fa ON a.actor_id = fa.actor_id
                             JOIN film f ON fa.film_id = f.film_id
                    WHERE f.rating = 'PG'
                    GROUP BY a.first_name, a.last_name, a.actor_id
                    ORDER BY nbFilms DESC, a.first_name, a.last_name, a.actor_id
                """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();
        return results.stream()
                .map(row -> new ActorInPGRating((String) row[0], (String) row[1], ((Number) row[2]).longValue()))
                .toList();
    }

    /**
     * Get actors who played in PG-rated films using JPQL.
     *
     * @return list of actors who played in PG-rated films
     */
    public List<ActorInPGRating> actorInPGRatings_JPQL() {
        TypedQuery<Object[]> query = em.createQuery(
                """
                            SELECT a.firstName, a.lastName, COUNT(f)
                            FROM actor a
                                     JOIN a.films f
                            WHERE f.rating = 'PG'
                            GROUP BY a.firstName, a.lastName, a.id
                            ORDER BY COUNT(f) DESC, a.firstName, a.lastName, a.id
                        """, Object[].class);

        return query.getResultList().stream()
                .map(row -> new ActorInPGRating((String) row[0], (String) row[1], ((Number) row[2]).longValue()))
                .toList();
    }

    /**
     * Get actors who played in PG-rated films using the Criteria API with string-based metamodel.
     *
     * @return list of actors who played in PG-rated films
     */
    public List<ActorInPGRating> actorInPGRatings_CriteriaString() {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var actor = query.from(Actor.class);
        var film = actor.join("films");
        query.multiselect(actor.get("firstName"), actor.get("lastName"), cb.count(film));
        query.where(cb.equal(film.get("rating"), Rating.PG));
        query.groupBy(actor.get("firstName"), actor.get("lastName"), actor.get("id"));
        query.orderBy(cb.desc(cb.count(film)),
                cb.asc(actor.get("firstName")),
                cb.asc(actor.get("lastName")),
                cb.asc(actor.get("id")));

        return em.createQuery(query).getResultList().stream()
                .map(row -> new ActorInPGRating((String) row[0], (String) row[1], ((Number) row[2]).longValue()))
                .toList();
    }

    /**
     * Get actors who played in PG-rated films using the Criteria API with metamodel.
     *
     * @return list of actors who played in PG-rated films
     */
    public List<ActorInPGRating> actorInPGRatings_CriteriaMetaModel() {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var actor = query.from(Actor.class);
        var film = actor.join(Actor_.films);
        query.multiselect(actor.get(Actor_.firstName), actor.get(Actor_.lastName), cb.count(film));
        query.where(cb.equal(film.get(Film_.rating), Rating.PG));
        query.groupBy(actor.get(Actor_.firstName), actor.get(Actor_.lastName), actor.get(Actor_.id));
        query.orderBy(cb.desc(cb.count(film)),
                cb.asc(actor.get(Actor_.firstName)),
                cb.asc(actor.get(Actor_.lastName)),
                cb.asc(actor.get(Actor_.id)));

        return em.createQuery(query).getResultList().stream()
                .map(row -> new ActorInPGRating((String) row[0], (String) row[1], ((Number) row[2]).longValue()))
                .toList();
    }
}
