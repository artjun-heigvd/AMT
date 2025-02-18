package com.example.starter.base.service;

import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.dto.UserDTO;
import com.example.starter.base.entity.Deck;
import com.example.starter.base.entity.Type;
import com.example.starter.base.repository.CardRepository;
import com.example.starter.base.repository.DeckRepository;
import com.example.starter.base.entity.Card;
import com.example.starter.base.repository.InventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.glassfish.jaxb.core.v2.TODO;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing deck operations.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class DeckService {

    @Inject
    DeckRepository deckRepository;

    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    CardRepository cardRepository;

    /**
     * Get deck by its ID
     *
     * @param deckId the deck id
     * @return the deck
     */
    @Transactional
    public DeckDTO getDeckById(int deckId) {
        return DeckDTO.convertDeckToDTO(deckRepository.getDeckById(deckId));
    }

    /**
     * Creates a new deck with the specified name and user.
     *
     * @param name   the name of the new deck
     * @param userId the id of the user
     * @return a DeckDTO representing the created deck
     */
    @Transactional
    public DeckDTO newDeck(String name, int userId) {
        return DeckDTO.convertDeckToDTO(deckRepository.create(name, userId));
    }

    /**
     * Return the creator of the deck
     *
     * @param deckId the id of the deck
     * @return the UserDTO of the creator of the deck
     */
    @Transactional
    public UserDTO getCreator(int deckId) {
        Deck deck = deckRepository.getDeckById(deckId);
        return UserDTO.convertUserToDTO(deck.getUser());
    }

    /**
     * Updates the information of an existing deck if there isn't more than 2 times the same card in
     * the deck
     *
     * @param id    the ID of the deck to update
     * @param type  the new type of the deck
     * @param cards the new list of cards in the deck
     * @return A DeckDTO representing the updated deck, or null if the update failed
     */
    @Transactional
    public DeckDTO updateInfos(int id, String type, List<CardDTO> cards) {
        Map<Integer, Integer> cardCount = new HashMap<>();
        for (var card : cards) {
            cardCount.merge(card.id(), 1, Integer::sum);
            if (cardCount.get(card.id()) > 2) {
                return null;
            }
        }

        List<Card> cardsConverted = cards.stream().map(c -> cardRepository.getById(c.id())).toList();
        return DeckDTO.convertDeckToDTO(deckRepository.updateInfo(id, Type.fromString(type), cardsConverted));
    }

    /**
     * Updates the name of an existing deck.
     *
     * @param id   the ID of the deck to update
     * @param name the new name of the deck
     * @return the new deck
     */
    @Transactional
    public DeckDTO updateName(int id, String name) {
        return DeckDTO.convertDeckToDTO(deckRepository.updateName(id, name));
    }

    /**
     * Delete a deck.
     *
     * @param id the ID of the deck to delete
     * @return the deleted deck
     */
    @Transactional
    public DeckDTO deleteDeck(int id) {
        return DeckDTO.convertDeckToDTO(deckRepository.delete(id));
    }

    /**
     * Get the average rating of a deck
     *
     * @param deckId the id of the deck
     * @return the average rating of the deck
     */
    @Transactional
    public double getAverageRating(int deckId) {
        return deckRepository.getAverageRating(deckId);
    }

    /**
     * Get the cards that the user is missing to complete the deck
     *
     * @param deckId the id of the deck
     * @param userId the id of the user
     * @return the missing cards
     */
    @Transactional
    public List<CardDTO> getMissingCards(int deckId, int userId) {
        List<CardDTO> ownedCards = new LinkedList<>(inventoryRepository.getByUserId(userId).getCards().stream().map(CardDTO::convertCardToDTO).toList());
        List<CardDTO> deckCards = deckRepository.getCards(deckId).stream().map(CardDTO::convertCardToDTO).toList();
        List<CardDTO> missingCards = new LinkedList<>();

        for (var card : deckCards) {
            if (ownedCards.contains(card)) {
                ownedCards.remove(card);
            } else {
                missingCards.add(card);
            }
        }

        return missingCards;
    }

    /**
     * Get all cards in a deck
     *
     * @param deckId the id of the deck
     * @return a list of all cards in the deck
     */
    @Transactional
    public List<CardDTO> getDeckCards(int deckId) {
        return deckRepository.getCards(deckId).stream().map(CardDTO::convertCardToDTO).toList();
    }

    /**
     * Get all decks by a username
     *
     * @param username the username of the user
     * @return a list of all decks by the user
     */
    @Transactional
    public List<DeckDTO> getDecksByUsername(String username) {
        return deckRepository.getByUsername(username).stream().map(DeckDTO::convertDeckToDTO).toList();
    }

    /**
     * Get all decks by a user id
     *
     * @param userId the id of the user
     * @return a list of all decks by the user
     */
    @Transactional
    public List<DeckDTO> getDecksByUserId(int userId) {
        return deckRepository.getDecksByUserId(userId).stream().map(DeckDTO::convertDeckToDTO).toList();
    }

    /**
     * Get all decks
     *
     * @return a list of all decks
     */
    @Transactional
    public List<DeckDTO> getAll() {
        return deckRepository.getAll().stream().map(DeckDTO::convertDeckToDTO).toList();
    }

    /**
     * Get all decks by type
     *
     * @param type the type of the deck
     * @return a list of all decks of the specified type
     */
    @Transactional
    public List<DeckDTO> getDecksByType(String type) {
        return deckRepository.getByType(Type.fromString(type)).stream().map(DeckDTO::convertDeckToDTO).toList();
    }

    /**
     * Get all decks ordered by rating
     *
     * @return a list of all decks ordered by rating
     */
    @Transactional
    public List<DeckDTO> getDecksOrderedByRating() {
        return deckRepository.getDecksOrderedByRating().stream().map(DeckDTO::convertDeckToDTO).toList();
    }

    /**
     * Return a map of cards and the number of occurrences of each card in a deck
     *
     * @param deckId the id of the deck
     * @return a map of cards and the number of occurrences of each card in the deck
     */
    @Transactional
    public Map<CardDTO, Integer> getCardCount(int deckId) {
        var cards = deckRepository.getCardCount(deckId);
        return cards.entrySet().stream().collect(Collectors.toMap(
                e -> CardDTO.convertCardToDTO(e.getKey()),
                Map.Entry::getValue
        ));
    }
}
