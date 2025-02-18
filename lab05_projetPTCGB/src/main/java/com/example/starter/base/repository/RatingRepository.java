package com.example.starter.base.repository;

import com.example.starter.base.entity.Deck;
import com.example.starter.base.entity.Rating;
import com.example.starter.base.entity.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Repository for ratings
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class RatingRepository {

    @Inject
    EntityManager em;

    /**
     * Create a rating
     *
     * @param rating the value of the rating
     * @param userId the id of the user
     * @param deckId the id of the deck
     * @return the rating
     */
    @Transactional
    public Rating createRating(int rating, int userId, int deckId) {
        Users user = em.find(Users.class, userId);
        Deck deck = em.find(Deck.class, deckId);
        if (user == null || deck == null) {
            throw new IllegalArgumentException("User or deck does not exist");
        }
        Rating newRating = new Rating();
        newRating.setValue(rating);
        newRating.setUser(user);
        newRating.setDeck(deck);
        em.persist(newRating);
        return newRating;
    }

    /**
     * Get ratings by deck id
     *
     * @param deckId the id of the deck
     * @return the rating
     */
    @Transactional
    public List<Rating> getByDeckId(int deckId) {
        Deck deck = em.find(Deck.class, deckId);
        if (deck == null) {
            throw new IllegalArgumentException("Deck does not exist");
        }
        return deck.getRatings();
    }

    /**
     * Update the value of a rating
     * @param rating the new value of the rating
     * @param ratingId the id of the rating
     * @return the new rating
     */
    @Transactional
    public Rating update(int rating, int ratingId){
        Rating currentRating = em.find(Rating.class, ratingId);
        if (currentRating == null ) {
            throw new IllegalArgumentException("Rating doesn't exists");
        }
        currentRating.setValue(rating);
        return currentRating;
    }

    /**
     * Get the rating of a user for a deck
     *
     * @param deckId the id of the deckId
     * @param userId the id of the user              
     * @return the rating or null if there is no rating
     */
    @Transactional
    public List<Rating> getByUserIdDeckId(int userId, int deckId){
        String query = """
                SELECT r
                FROM rating r
                WHERE r.user.id = :userId AND r.deck.id = :deckId
                """;
        return em.createQuery(query, Rating.class)
                .setParameter("deckId", deckId)
                .setParameter("userId", userId)
                .getResultList();
    }
}
