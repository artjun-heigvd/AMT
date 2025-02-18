package com.example.starter.base.dto;

import com.example.starter.base.entity.Card;

/**
 * Data Transfer Object for the Card entity.
 * @param id the id of the card
 * @param name the name of the card
 * @param rarity the rarity of the card
 * @param type the type of the card
 * @param health the health of the card
 * @param stage the stage of the card
 * @param craftingCost the crafting cost of the card
 * @param image the image of the card
 * @param extension the extension of the card
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public record CardDTO(Integer id, String name, String rarity, String type, Integer health, String stage, Integer craftingCost, String image, String extension) {

    public static CardDTO convertCardToDTO(Card card){
        return new CardDTO(card.getId(), card.getName(), card.getRarity().toString(), card.getType().toString(), card.getHealth(), card.getStage().toString(), card.getCraftingCost(), card.getImage(), card.getExtension());
    }
}
