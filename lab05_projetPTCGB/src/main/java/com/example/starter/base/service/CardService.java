package com.example.starter.base.service;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.entity.Card;
import com.example.starter.base.entity.Rarity;
import com.example.starter.base.repository.CardRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Service class for managing cards.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class CardService {

    @Inject
    CardRepository cardRepository;

    /**
     * Retrieves a card by its ID and converts it to a CardDTO.
     *
     * @param id the ID of the card
     * @return the CardDTO representation of the card
     */
    @Transactional
    public CardDTO getCardById(int id) {
        return CardDTO.convertCardToDTO(cardRepository.getById(id));
    }

    /**
     * Retrieves cards with a specified rarity threshold, ordered by a specified column.
     *
     * @param rarity  the rarity threshold
     * @param aboveEq if true, retrieves cards with rarity above or equal to the specified rarity; otherwise, below
     * @param column  the column to order by
     * @param asc     if true, orders in ascending order; otherwise, descending
     * @return a list of CardDTOs representing the ordered cards
     */
    @Transactional
    public List<CardDTO> getThresholdRarityOrdered(Rarity rarity, boolean aboveEq, String column, boolean asc) {
        var cards = cardRepository.getThresholdRarityOrdered(rarity, aboveEq, column, asc);
        return cards.stream().map(CardDTO::convertCardToDTO).toList();
    }

    /**
     * Retrieves all cards ordered by a specified column.
     *
     * @param column the column to order by
     * @param asc    if true, orders in ascending order; otherwise, descending
     * @return a list of CardDTOs representing the ordered cards
     */
    @Transactional
    public List<CardDTO> getAllOrdered(String column, boolean asc) {
        var cards = cardRepository.getAllOrdered(column, asc);
        return cards.stream().map(CardDTO::convertCardToDTO).toList();
    }

    /**
     * Retrieves the PNG image from a card by its ID.
     *
     * @param cardId the ID of the card
     * @return an InputStream of the PNG image
     * @throws IOException if an I/O error occurs
     */
    @Transactional
    public InputStream getCardImage(int cardId) throws IOException {
        Card card = cardRepository.getById(cardId);
        if (card == null || card.getImage() == null) {
            throw new IllegalArgumentException("Card or image not found");
        }
        URL url = new URL(card.getImage());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "image/png");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to retrieve image: " + connection.getResponseMessage());
        }

        return connection.getInputStream();
    }
}
