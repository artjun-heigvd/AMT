package ch.heigvd.amt.jpa.repository;

import ch.heigvd.amt.jpa.entity.Actor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Repository class for managing Actor entities in the database.
 * Provides methods for creating, reading, updating, and deleting Actor records.
 * This class uses an {@link EntityManager} to interact with the persistence context.
 *
 * @author Eva Ray
 * @author Rachel Trachida
 * @author Arthur Junod
 * @author Edwin Häffner
 */
@ApplicationScoped
public class ActorRepository {

    @Inject
    private EntityManager em;

    /**
     * Converts an {@link Actor} entity to a {@link ActorDTO}.
     *
     * @param actor the Actor entity to convert
     * @return an ActorDTO representing the actor, or null if the actor is null
     */
    private ActorDTO fromEntityToDTO(Actor actor) {
        if (actor == null) {
            return null;
        }
        return new ActorDTO(actor.getId(), actor.getFirstName(), actor.getLastName());
    }

    /**
     * Data Transfer Object (DTO) for Actor entity.
     * Encapsulates an Actor's ID, first name, and last name.
     */
    public record ActorDTO(Integer id, String firstName, String lastName) {
    }

    /**
     * Retrieves an actor from the database by its ID.
     *
     * @param id the ID of the actor to retrieve
     * @return an {@link ActorDTO} representing the retrieved actor, may be null if no actor with the given ID exists
     */
    @Transactional
    public ActorDTO read(Integer id) {
        Actor actor = em.find(Actor.class, id);
        return this.fromEntityToDTO(actor);
    }

    /**
     * Creates a new actor in the database.
     *
     * @param firstName the first name of the new actor
     * @param lastName  the last name of the new actor
     * @return the ID of the newly created actor
     */
    @Transactional
    public Integer create(String firstName, String lastName) {
        Actor actor = new Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);

        em.persist(actor);
        return actor.getId();
    }

    /**
     * Updates an existing actor in the database.
     *
     * @param id        the ID of the actor to update
     * @param firstName the new first name of the actor
     * @param lastName  the new last name of the actor
     * @throws IllegalArgumentException if no actor with the given ID exists
     */
    @Transactional
    public void update(Integer id, String firstName, String lastName) {
        Actor actor = em.find(Actor.class, id);

        if (actor == null) {
            throw new IllegalArgumentException("Actor with id " + id + " does not exist");
        }

        actor.setFirstName(firstName);
        actor.setLastName(lastName);

        em.merge(actor);
    }

    /**
     * Deletes an actor from the database by its ID.
     *
     * @param id the ID of the actor to delete
     * @throws IllegalArgumentException if no actor with the given ID exists
     */
    @Transactional
    public void delete(Integer id) {
        Actor actor = em.find(Actor.class, id);

        if (actor == null) {
            throw new IllegalArgumentException("Actor with id " + id + " does not exist");
        }
        em.remove(actor);
    }
}