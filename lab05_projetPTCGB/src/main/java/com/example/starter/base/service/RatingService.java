package com.example.starter.base.service;

import com.example.starter.base.dto.RatingDTO;
import com.example.starter.base.entity.Users;
import com.example.starter.base.repository.RatingRepository;
import com.example.starter.base.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.transaction.Transactional;
import jakarta.jms.ConnectionFactory;

import java.util.List;

/**
 * Service class for managing rating operations.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class RatingService {

    @Inject
    RatingRepository ratingRepository;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    DeckService deckService;

    @Inject
    UserService userService;

    /**
     * Allow us to send a message to the message broker so that it can be consumed by the MailSender
     *
     * @param userId the id of user that rated the deck
     * @param deckId the id of the deck that was rated
     * @param rating value of the rating
     * @param action action executed
     */
    public void sendRatingMessage(int userId, int deckId, int rating, String action) {
        try (var context = connectionFactory.createContext()) {
            var queue = context.createQueue("queue");
            var producer = context.createProducer();
            var message = context.createTextMessage();

            var email = deckService.getCreator(deckId).email();
            var deckName = deckService.getDeckById(deckId).name();
            var raterName = userService.getById(userId).username();

            message.setStringProperty("to", email);
            message.setStringProperty("deck", deckName);
            message.setStringProperty("rater", raterName);
            message.setStringProperty("rating", String.valueOf(rating));
            message.setStringProperty("action", action);

            producer.send(queue, message);
            System.out.println("Message sent: " + message);
        } catch (Exception e) {
            System.out.println("Couldn't send the rating through the queue. Exception message: " + e.getMessage());
        }
    }

    /**
     * Creates a new rating for a specified user and deck or returns null if the user already rated the deck
     *
     * @param rating the rating value
     * @param userId the ID of the user
     * @param deckId the ID of the deck
     * @return a RatingDTO representing the created rating or null if the user already rated the deck
     */
    @Transactional
    public RatingDTO createOrUpdateRating(int rating, int userId, int deckId) {
        List<Rating> currentRating = ratingRepository.getByUserIdDeckId(userId, deckId);
        if (!currentRating.isEmpty()) {
            sendRatingMessage(userId, deckId, rating, "Rating updated");
            return RatingDTO.convertRatingToDTO(ratingRepository.update(rating, currentRating.get(0).getId()));
        }
        sendRatingMessage(userId, deckId, rating, "New rating");
        return RatingDTO.convertRatingToDTO(ratingRepository.createRating(rating, userId, deckId));
    }

    /**
     * Get the rating a user gave to a deck (null if no rating)
     *
     * @param userId the id of the user
     * @param deckId the id of the deck
     * @return the rating or null if it doesn't exist
     */
    @Transactional
    public RatingDTO getRatingUserDeck(int userId, int deckId) {
        List<Rating> rating = ratingRepository.getByUserIdDeckId(userId, deckId);
        if (rating.isEmpty()) {
            return null;
        } else {
            return RatingDTO.convertRatingToDTO(rating.get(0));
        }
    }

    @Transactional
    public int getNbRating(int deckId) {
        return ratingRepository.getByDeckId(deckId).size();
    }

    /**
     * Retrieves all ratings for a specified deck.
     *
     * @param deckId the ID of the deck
     * @return a list of RatingDTOs representing the ratings for the deck
     */
    @Transactional
    public List<RatingDTO> getByDeckId(int deckId) {
        var ratings = ratingRepository.getByDeckId(deckId);
        return ratings.stream().map(RatingDTO::convertRatingToDTO).toList();
    }
}
