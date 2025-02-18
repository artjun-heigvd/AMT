package com.example.starter.base.repository;

import com.example.starter.base.entity.Card;
import com.example.starter.base.entity.Inventory;
import com.example.starter.base.entity.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository for inventories
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class InventoryRepository {

    @Inject
    EntityManager em;

    @Inject
    UserRepository userRepository;

    /**
     * Create an inventory for a user
     * @param user the user to create the inventory for
     * @return the created inventory
     */
    @Transactional
    public Inventory create(Users user){
        Inventory inventory = new Inventory();
        inventory.setUser(user);
        em.persist(inventory);
        return inventory;
    }

    /**
     * Add a card to an inventory
     * @param inventoryId   id of the inventory
     * @param cardId        id of the card
     */
    @Transactional
    public void addCard(int inventoryId, int cardId){
        Card card = em.find(Card.class, cardId);
        Inventory inventory = em.find(Inventory.class, inventoryId);
        if (card == null || inventory == null){
            throw new IllegalArgumentException("Card or inventory does not exist");
        }
        inventory.addCard(card);
        em.merge(inventory);
    }

    /**
     *  Get the user of an inventory
     * @param inventoryId  id of the inventory
     * @return the user of the inventory
     */
    @Transactional
    public Users getUserByInventoryId(int inventoryId){
        Inventory inventory = em.find(Inventory.class, inventoryId);
        if (inventory == null){
            throw new IllegalArgumentException("Inventory does not exist");
        }
        return inventory.getUser();
    }

    /**
     * Remove a card from an inventory
     * @param inventoryId the id of the inventory
     * @param cardId the id of the card to remove
     */
    @Transactional
    public void removeCard(int inventoryId, int cardId){
        Card card = em.find(Card.class, cardId);
        Inventory inventory = em.find(Inventory.class, inventoryId);
        if (card == null || inventory == null){
            throw new IllegalArgumentException("Card or inventory does not exist");
        }
        inventory.removeCard(card);
        em.merge(inventory);
    }

    /**
     * Get all cards in an inventory
     * @param inventoryId the id of the inventory
     * @return a list of all cards in the inventory
     */
    @Transactional
    public List<Card> getAllCards(int inventoryId){
        Inventory inventory = em.find(Inventory.class, inventoryId);
        if (inventory == null){
            throw new IllegalArgumentException("Inventory does not exist");
        }
        return inventory.getCards();
    }

    /**
     * Get the inventory of a user
     * @param userId the user id
     * @return the inventory of the user
     */
    @Transactional
    public Inventory getByUserId(int userId) {
        Users user = userRepository.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        try {
            return em.createQuery("SELECT i FROM inventory i WHERE i.user.id = :userId", Inventory.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Return a map of cards and the number of occurrences of each card in an inventory
     * @param inventoryId the id of the inventory
     * @return a map of cards and the number of occurrences of each card in the inventory
     */
    @Transactional
    public Map<Card, Integer> getCardCount(int inventoryId){
        String query = """
            SELECT c, COUNT(c)
            FROM card c
            JOIN c.inventories i
            WHERE i.id = :inventoryId
            GROUP BY c
            """;
        List<Object[]> results = em.createQuery(query, Object[].class)
                .setParameter("inventoryId", inventoryId)
                .getResultList();
        return results.stream().collect(Collectors.toMap(
                o -> (Card) o[0],
                o -> ((Long) o[1]).intValue()
        ));
    }

    /**
     * Return a map of user owned cards and the number of occurrences of each card in an inventory
     * @param userId the id of the user
     * @return a map of cards and the number of occurrences of each card in the inventory
     */
    @Transactional
    public Map<Card, Integer> getCardCountByUserId(int userId){
        String query = """
            SELECT c, COUNT(c)
            FROM card c
            JOIN c.inventories i
            WHERE i.user.id = :userId
            GROUP BY c
            """;
        List<Object[]> results = em.createQuery(query, Object[].class)
                .setParameter("userId", userId)
                .getResultList();
        return results.stream().collect(Collectors.toMap(
                o -> (Card) o[0],
                o -> ((Long) o[1]).intValue()
        ));
    }

    /**
     * Return a map of all existing cards and the number of occurrences of each card in the user inventory
     * @param userId the id of the user
     * @return a map of cards and the number of occurrences of each card in the inventory
     */
    @Transactional
    public Map<Card, Integer> getCardCountForAllCardsByUserId(int userId) {
        {
            String query = """
                SELECT c, COUNT(i)
                FROM card c
                LEFT JOIN c.inventories i ON i.user.id = :userId
                GROUP BY c
                ORDER BY c.id
                
                """;

        List<Object[]> results = em.createQuery(query, Object[].class)
                .setParameter("userId", userId)
                .getResultList();

        return results.stream().collect(Collectors.toMap(
                o -> (Card) o[0],
                o -> ((Long) o[1] != null ? ((Long) o[1]).intValue() : 0),
        (e1, e2) -> e1, LinkedHashMap::
        new));
    }
    }

}
