package com.example.starter.base.repository;

import com.example.starter.base.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository for decks
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class DeckRepository {

    @Inject
    EntityManager em;

    /**
     * Get deck by its ID
     *
     * @param deckId the deck id
     * @return the deck
     */
    @Transactional
    public Deck getDeckById(int deckId) {
        return em.find(Deck.class, deckId);
    }

    /**
     * Get all decks
     *
     * @return all the decks
     */
    @Transactional
    public List<Deck> getAll() {
        return em.createQuery("""
                        SELECT d
                        FROM deck d
                        """, Deck.class)
                .getResultList();
    }

    /**
     * Get all decks by user id
     *
     * @param userId the id of the user
     * @return the decks
     */
    @Transactional
    public List<Deck> getDecksByUserId(int userId) {
        String query = """
                        SELECT d
                        FROM deck d
                        WHERE d.user.id = :userId
                """;
        return em.createQuery(query, Deck.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public List<Deck> getByUsername(String username) {
        String query = """
                        SELECT d
                        FROM deck d
                        WHERE d.user.username = :username
                """;
        return em.createQuery(query, Deck.class)
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * Get all decks by type
     *
     * @param type the type of the deck
     * @return the decks
     */
    @Transactional
    public List<Deck> getByType(Type type) {
        String query = """
                        SELECT d
                        FROM deck d
                        WHERE d.type = :type
                """;
        return em.createQuery(query, Deck.class)
                .setParameter("type", type)
                .getResultList();
    }

    /**
     * Get the 10 decks with the most ratings
     *
     * @return the decks
     */
    @Transactional
    public List<Deck> get10MostRated() {
        String query = """
                        SELECT d
                        FROM deck d
                        ORDER BY d.ratings.size DESC
                        LIMIT 10
                """;
        return em.createQuery(query, Deck.class)
                .getResultList();
    }

    /**
     * Get the x first decks with the highest average rating
     *
     * @param x the number of decks to get
     * @return the decks
     */
    @Transactional
    List<Deck> getXBestRated(int x) {
        String query = """
                SELECT d
                FROM deck d
                JOIN d.ratings r
                GROUP BY d
                ORDER BY AVG(r.value) DESC
                LIMIT :x
                """;
        return em.createQuery(query, Deck.class)
                .getResultList();
    }

    /**
     * Get all buildable decks by a user
     *
     * @param userId the id of the user
     * @return the buildable deck by the user
     */
    @Transactional
    public List<Deck> getBuildableDecks(int userId) {
        String query = """
                SELECT d
                FROM deck d
                JOIN d.cards c
                JOIN inventory inv
                WHERE inv.user.id = :userId
                """;
        return em.createQuery(query, Deck.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Create a deck
     *
     * @param name   the name of the deck
     * @param userId the id of the user
     * @return the created deck
     */
    @Transactional
    public Deck create(String name, Integer userId) {
        Users user = em.find(Users.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Deck deck = new Deck();
        deck.setName(name);
        deck.setCards(new ArrayList<>());
        deck.setUser(user);

        em.persist(deck);
        return deck;
    }

    /**
     * Update a deck's card and type (reset the rating linked to this deck)
     *
     * @param deckId the id of the deck
     * @param type   the type of the deck
     * @return the updated deck
     */
    @Transactional
    public Deck updateInfo(Integer deckId, Type type, List<Card> cards) {
        Deck deck = em.find(Deck.class, deckId);
        if (deck == null) {
            throw new IllegalArgumentException("Deck not found");
        }
        List<Rating> ratingsToRemove = deck.getRatings();
        deck.resetRatings();
        deck.setType(type);
        deck.setCards(cards);
        return deck;
    }

    /**
     * Update the name of the deck (avoid resetting the rating for a change on the name)
     *
     * @param deckId the id of the deck we want to change the name
     * @param name   the new name of the deck
     * @return the updated deck
     */
    @Transactional
    public Deck updateName(Integer deckId, String name) {
        Deck deck = em.find(Deck.class, deckId);
        if (deck == null) {
            throw new IllegalArgumentException("Deck not found");
        }
        deck.setName(name);
        return deck;
    }

    /**
     * Delete a deck
     *
     * @param id the id of the deck to delete
     */
    @Transactional
    public Deck delete(int id) {
        Deck deck = em.find(Deck.class, id);
        if (deck == null) {
            throw new IllegalArgumentException("Deck not found");
        }
        em.remove(deck);
        return deck;
    }

    /**
     * Get the average rating of a deck
     *
     * @param deckId the id of the deck
     * @return the average rating
     */
    @Transactional
    public double getAverageRating(int deckId) {
        String query = """
                        SELECT AVG(r.value)
                        FROM rating r
                        WHERE r.deck.id = :deckId
                    """;
        Double averageRating = em.createQuery(query, Double.class)
                .setParameter("deckId", deckId)
                .getSingleResult();
        return averageRating != null ? averageRating : 0.0;
    }

    /**
     * Get the cards in a deck
     *
     * @param deckId the id of the deck
     * @return the cards
     */
    @Transactional
    public List<Card> getCards(int deckId) {
        String query = """
                        SELECT c.*
                        FROM card c
                        JOIN deck_card dc on c.card_id = dc.card_id
                        WHERE dc.deck_id = ?1
                """;
        return em.createNativeQuery(query, Card.class)
                .setParameter(1, deckId)
                .getResultList();
    }

    /**
     * Get the decks ordered by rating
     *
     * @return the decks
     */
    @Transactional
    public List<Deck> getDecksOrderedByRating() {
        String query = """
                        SELECT d
                        FROM deck d
                        LEFT JOIN d.ratings r
                        GROUP BY d
                        ORDER BY AVG(r.value) DESC
                """;
        return em.createQuery(query, Deck.class)
                .getResultList();
    }

    /**
     * Return a map of cards and the number of occurrences of each card in a deck
     * @param deckId the id of the deck
     * @return a map of cards and the number of occurrences of each card in the deck
     */
    @Transactional
    public Map<Card, Integer> getCardCount(int deckId) {
        String query = """
                        SELECT c, COUNT(c)
                        FROM card c
                        JOIN c.decks dc
                        WHERE dc.id = :deckId
                        GROUP BY c
                """;
        List<Object[]> results = em.createQuery(query, Object[].class)
                .setParameter("deckId", deckId)
                .getResultList();
        return results.stream().collect(Collectors.toMap(
                o -> (Card) o[0],
                o -> ((Long) o[1]).intValue()
        ));
    }
}
