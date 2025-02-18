package com.example.starter.base.dto;

import com.example.starter.base.entity.Rating;

/**
 * Data Transfer Object for the Rating entity.
 * @param id the id of the rating
 * @param value the value of the rating
 * @param deckId the id of the deck that the rating is associated with
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public record RatingDTO(Integer id, Integer value, Integer deckId) {

        public static RatingDTO convertRatingToDTO(Rating rating){
            return new RatingDTO(rating.getId(), rating.getValue(), rating.getDeck().getId());
        }
}
