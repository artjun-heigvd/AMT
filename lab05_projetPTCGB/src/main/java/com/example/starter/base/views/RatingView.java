package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.dto.RatingDTO;
import com.example.starter.base.service.DeckService;
import com.example.starter.base.service.RatingService;
import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.decks.DeckContent;
import com.example.starter.base.utilities.StarRating;
import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * View for the deck rating
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_RATING)
public class RatingView extends AuthorizedView {
    private int deckId;

    @Inject
    DeckService deckService;

    @Inject
    RatingService ratingService;

    List<CardDTO> missingCards;

    /**
     * Method that is called before the view is entered. Get the deck id from the route parameters and initialize the view.
     *
     * @param event the before enter event
     */
    public void beforeEnter(BeforeEnterEvent event) {
        deckId = Integer.parseInt(event.getRouteParameters().get("deckId").orElse("1"));
        initializeView();
    }

    /**
     * Constructor for the RatingView.
     */
    public void initializeView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        DeckDTO deck = deckService.getDeckById(deckId);
        List<CardDTO> deckCards = deckService.getDeckCards(deckId);
        DeckContent deckContent = new DeckContent(deck, deckCards);

        if(UserHelper.getCurrentId() == -1){
             missingCards = new ArrayList<>();
        } else {
            missingCards = deckService.getMissingCards(deckId, UserHelper.getCurrentId());
        }
        deckContent.showWhichCardAreNotOwned(missingCards);

        // Create MenuBar
        MenuBar menuBar = new MenuBar();

        // Create SplitLayout for the main content
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);

        // Create the rating panel (left side)
        VerticalLayout ratingLayout = new VerticalLayout();
        ratingLayout.setPadding(true);
        ratingLayout.setSpacing(true);

        // Add rating title and description
        H3 ratingTitle = new H3("Deck Rating");
        // Display current rating with stars (read-only)
        Paragraph currentRatingLabel = new Paragraph("Current Rating:");
        StarRating currentRating = new StarRating();
        currentRating.setRating(deckService.getAverageRating(deckId));
        currentRating.setReadOnly(true);

        // Add new rating input with stars
        Paragraph yourRatingLabel = new Paragraph("Your Rating:");
        StarRating ratingInput = new StarRating();

        setUserRating(ratingInput);

        // Add submit rating button
        Button submitRating = new Button("Submit Rating", event -> {
            Double rating = ratingInput.getRating();
            int nbRating = ratingService.getNbRating(deckId);
            if (rating > 0) {

                ratingService.createOrUpdateRating(rating.intValue(), UserHelper.getCurrentId(), deckId);

                currentRating.setRating(
                        (deckService.getAverageRating(deckId) * nbRating + rating) / (nbRating + 1)
                        ,true);
                Notification.show("Rating submitted successfully! : " + rating);
                setUserRating(ratingInput);
            } else {
                Notification.show("Please select a rating before submitting");
            }
        });

        // Add clone deck button
        Button cloneDeck = new Button("Clone Deck", event -> {
        if (missingCards.isEmpty()) {

            DeckDTO newDeck = deckService.newDeck(deck.name(), UserHelper.getCurrentId());
            deckService.updateInfos(newDeck.id(), deck.type(), deckCards);

            getUI().ifPresent(ui ->
                    ui.navigate(Constants.ROUTE_DECK_BUILDING.replace(":deckId", String.valueOf(newDeck.id()))));
            Notification.show("Deck cloned successfully!");
        } else {
            Notification.show("You must own all cards in the deck to clone it.");
        }

        });



        // Create the deck display panel (right side)
        VerticalLayout deckLayout = new VerticalLayout();
        deckLayout.setPadding(true);
        deckLayout.setSpacing(true);

        H3 deckTitle = new H3("Deck Contents");
        deckLayout.add(deckTitle, deckContent);

        if(UserHelper.isLoggedIn()){
            // Add components to rating layout
            ratingLayout.add(
                    ratingTitle,
                    currentRatingLabel,
                    currentRating,
                    yourRatingLabel,
                    ratingInput,
                    submitRating,
                    cloneDeck
            );
        } else {
            ratingLayout.add(
                    ratingTitle,
                    currentRatingLabel,
                    currentRating
            );
        }


        // Add the panels to the split layout
        splitLayout.addToPrimary(ratingLayout);
        splitLayout.addToSecondary(deckLayout);

        // Set initial split position (30% left, 70% right)
        splitLayout.setSplitterPosition(30);

        // Add components to main layout
        add(menuBar, splitLayout);
        expand(splitLayout);
    }

    private void setUserRating(StarRating ratingInput){
        RatingDTO rating = ratingService.getRatingUserDeck(UserHelper.getCurrentId(), deckId);

        if(UserHelper.isLoggedIn()){
            ratingInput.setRating(rating == null ? 0 : rating.value().doubleValue()); // Display the rating the user gave
        }
    }
}