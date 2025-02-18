package com.example.starter.base.service;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.dto.InventoryDTO;
import com.example.starter.base.dto.UserDTO;
import com.example.starter.base.repository.InventoryRepository;
import com.example.starter.base.entity.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing inventory operations.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryRepository inventoryRepository;

    /**
     * Creates a new inventory for the specified user.
     *
     * @param user the user who owns the inventory
     * @return an InventoryDTO representing the created inventory
     */
    @Transactional
    public InventoryDTO create(Users user) {
        return InventoryDTO.convertInventoryToDTO(inventoryRepository.create(user), user);
    }

    /**
     * Adds a card to the inventory.
     *
     * @param cardId      the ID of the card to add
     * @param inventoryId the ID of the inventory to which the card will be added
     */
    @Transactional
    public void addCard(int cardId, int inventoryId) {
        inventoryRepository.addCard(inventoryId, cardId);
    }

    /**
     * Removes a card from the inventory.
     *
     * @param cardId      the ID of the card to remove
     * @param inventoryId the ID of the inventory from which the card will be removed
     */
    @Transactional
    public void removeCard(int cardId, int inventoryId) {
        inventoryRepository.removeCard(inventoryId, cardId);
    }

    /**
     * Retrieves the user associated with a given inventory ID and converts it to a UserDTO.
     *
     * @param inventoryId the ID of the inventory
     * @return the UserDTO representation of the user associated with the inventory
     */
    @Transactional
    public UserDTO getUserByInventoryId(int inventoryId) {
        return UserDTO.convertUserToDTO(inventoryRepository.getUserByInventoryId(inventoryId));
    }

    /**
     * Retrieves all cards in the inventory and converts them to a list of CardDTOs.
     *
     * @param inventoryId the ID of the inventory
     * @return a list of CardDTOs representing all cards in the inventory
     */
    @Transactional
    public List<CardDTO> getAllCards(int inventoryId) {
        var cards = inventoryRepository.getAllCards(inventoryId);
        return cards.stream().map(CardDTO::convertCardToDTO).toList();
    }

    /**
     * Rerun a map of card and the number of their occurrences in the inventory
     *
     * @param inventoryId the id of the inventory
     * @return a map of card and the number of their occurrences in the inventory
     */
    @Transactional
    public Map<CardDTO, Integer> getCardCount(int inventoryId) {
        var cardCounts = inventoryRepository.getCardCount(inventoryId);
        return cardCounts.entrySet().stream().collect(Collectors
                .toMap(entry -> CardDTO.convertCardToDTO(entry.getKey()), Map.Entry::getValue));
    }

    /**
     * Rerun a map of user owned cards and the number of their occurrences in the inventory
     *
     * @param userId the id of the inventory
     * @return a map of card and the number of their occurrences in the inventory
     */
    @Transactional
    public Map<CardDTO, Integer> getCardCountByUserId(int userId) {
        var cardCounts = inventoryRepository.getCardCountByUserId(userId);
        return cardCounts.entrySet().stream().collect(Collectors
                .toMap(entry -> CardDTO.convertCardToDTO(entry.getKey()), Map.Entry::getValue));
    }

    /**
     * Rerun a map of all existing card and the number of their occurrences in the user inventory
     *
     * @param userId the id of the inventory
     * @return a map of card and the number of their occurrences in the inventory
     */
    @Transactional
    public Map<CardDTO, Integer> getCardCountForAllCardsByUserId(int userId) {
        var cardCounts = inventoryRepository.getCardCountForAllCardsByUserId(userId);

        return cardCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> CardDTO.convertCardToDTO(entry.getKey()),
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
