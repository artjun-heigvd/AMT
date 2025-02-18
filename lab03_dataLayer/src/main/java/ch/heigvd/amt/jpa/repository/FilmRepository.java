package ch.heigvd.amt.jpa.repository;

import ch.heigvd.amt.jpa.entity.Film;
import ch.heigvd.amt.jpa.entity.Language;
import ch.heigvd.amt.jpa.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

/**
 * Repository class for managing Film entities in the database.
 * Provides methods for creating, reading, updating, and deleting Film records.
 * This class uses an {@link EntityManager} to interact with the persistence context.
 *
 * @author Eva Ray
 * @author Rachel Tranchida
 * @author Arthur Junod
 * @author Edwin Häffner
 */
@ApplicationScoped
public class FilmRepository {

    @Inject
    private EntityManager em;

    public record FilmDTO(Integer id, String title, String language, String rating) {
    }

    /**
     * Converts a {@link Film} entity to a {@link FilmDTO}.
     *
     * @param film the Film entity to convert
     * @return a FilmDTO representing the film, or null if the film is null
     */
    private FilmDTO fromEntityToDTO(Film film) {
        if (film == null) {
            return null;
        }
        return new FilmDTO(film.getId(), film.getTitle(), film.getLanguage().getName(), film.getRating().toString());
    }

    /**
     * Retrieves a film from the database by its ID.
     *
     * @param id the ID of the film to retrieve
     * @return a {@link FilmDTO} representing the retrieved film, may be null if no film with the given ID exists
     */
    public FilmDTO read(Integer id) {
        Film film = em.find(Film.class, id);
        return this.fromEntityToDTO(film);
    }

    /**
     * Creates a new film in the database.
     * If the specified language does not exist, a new {@link Language} is created and persisted.
     *
     * @param title    the title of the new film
     * @param language the name of the language of the film
     * @param rating   the rating of the film as a string (e.g., "PG-13")
     * @return the ID of the newly created film
     */
    @Transactional
    public Integer create(String title, String language, String rating) {
        Film film = new Film();
        film.setTitle(title);

        Language lang;
        try {
            lang = em.createNamedQuery("Language.findByName", Language.class)
                    .setParameter("name", language)
                    .getSingleResult();
        } catch (NoResultException e) {
            lang = new Language();
            lang.setName(language);
            em.persist(lang);
        }

        film.setLanguage(lang);
        film.setRating(Rating.fromString(rating));
        em.persist(film);
        return film.getId();
    }

    /**
     * Updates an existing film in the database.
     * If the specified language does not exist, a new {@link Language} is created and persisted.
     *
     * @param id       the ID of the film to update
     * @param title    the new title of the film
     * @param language the new language of the film
     * @param rating   the new rating of the film
     * @throws IllegalArgumentException if no film with the given ID exists
     */
    @Transactional
    public void update(Integer id, String title, String language, String rating) {
        Film film = em.find(Film.class, id);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + id + " does not exist");
        }

        Language lang;
        try {
            lang = em.createNamedQuery("Language.findByName", Language.class)
                    .setParameter("name", language)
                    .getSingleResult();
        } catch (NoResultException e) {
            lang = new Language();
            lang.setName(language);
            em.persist(lang);
        }

        film.setTitle(title);
        film.setLanguage(lang);
        film.setRating(Rating.fromString(rating));
        em.merge(film);
    }

    /**
     * Deletes a film from the database by its ID.
     *
     * @param id the ID of the film to delete
     * @throws IllegalArgumentException if no film with the given ID exists
     */
    @Transactional
    public void delete(Integer id) {
        Film film = em.find(Film.class, id);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + id + " does not exist");
        }
        em.remove(film);
    }
}
