package com.example.starter.base.dto;

import com.example.starter.base.entity.Deck;

/**
 * Data Transfer Object for the Deck entity.
 * @param id id of the deck
 * @param name name of the deck
 * @param type type of the deck
 * @param userId id of the user who owns the deck
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public record DeckDTO(Integer id, String name, String type, Integer userId) {

    public static DeckDTO convertDeckToDTO(Deck deck){
        return new DeckDTO(deck.getId(), deck.getName(), deck.getType().toString(), deck.getUser().getId());
    }
}
