package com.example.starter.base.utilities.decks;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.utilities.cards.CardBasic;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.dom.Style.JustifyContent.FLEX_START;

/**
 * DeckContent is a class that extends VerticalLayout.
 * It displays the deck title, total card count, and the cards in the deck.
 * It also shows which cards are not owned by the user.
 * It allows the user to add and remove cards from the deck
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */

public class DeckContent extends VerticalLayout {
    private final Span totalCardCountText;
    private int totalCardCount = 0;
    private static final int MAX_CARDS = 20;
    private final FlexLayout cardContainer;
    H3 deckTitle = new H3();
    Map<Integer, Long> mapDeckCardCount;

    /**
     * Constructor for DeckContent.
     *
     * @param deck      DeckDTO object.
     * @param deckCards List of CardDTO objects.
     */
    public DeckContent(DeckDTO deck, List<CardDTO> deckCards) {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        deckTitle.setText(deck.name());
        mapDeckCardCount = new HashMap<>();
        for (CardDTO card : deckCards) {
            mapDeckCardCount.merge(card.id(), 1L, Long::sum);
        }
        totalCardCount = deckCards.size();
        totalCardCountText = new Span("Total cards: " + totalCardCount);

        cardContainer = new FlexLayout();
        cardContainer.setSizeFull();
        cardContainer.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexWrap(Style.FlexWrap.WRAP)
                .setMargin("16px")
                .setPadding("16px")
                .setOverflow(Style.Overflow.AUTO)
                .setJustifyContent(FLEX_START)
                .set("gap", "16px");



        for (CardDTO card : deckCards) {
            cardContainer.add(new CardBasic(card));
        }
        updateTotalCardCountText();

        add(deckTitle, totalCardCountText, cardContainer);
    }

    /**
     * Shows which cards are not owned by the user.
     *
     * @param missingCards List of CardDTO objects.
     */
    public void showWhichCardAreNotOwned(List<CardDTO> missingCards) {
        boolean notLoggedIn = missingCards.isEmpty();

        var missingCardId = new ArrayList<>(missingCards.stream().map(CardDTO::id).toList());
        var idsToRemove = new ArrayList<Integer>();

        // Iterate through deck cards and update the UI
        cardContainer.getChildren()
                .filter(component -> component instanceof CardBasic)
                .map(component -> (CardBasic) component)
                .forEach(cardBasic -> {
                    if (!notLoggedIn && missingCardId.contains(cardBasic.getCardId())) {
                        cardBasic.getStyle()
                                .set("background-color", "transparent")
                                .set("filter", "brightness(75%) saturate(140%)")
                                .set("border", "2px solid black")
                                .set("position", "relative");
                        cardBasic.getCardImage().getStyle().set("transform", "scale(0.9)");

                        Span notOwnedLabel = new Span("Not Owned");
                        notOwnedLabel.getStyle()
                                .set("position", "absolute")
                                .set("top", "5px")
                                .set("left", "5px")
                                .set("background-color", "rgba(0, 0, 0, 0.7)")
                                .set("color", "white")
                                .set("padding", "2px 5px")
                                .set("border-radius", "3px");
                        cardBasic.add(notOwnedLabel);

                        idsToRemove.add(cardBasic.getCardId());
                    }
                });

        missingCardId.removeAll(idsToRemove);
    }

    /**
     * Adds a card to the deck.
     *
     * @param card CardDTO object.
     */
    public void addCard(CardDTO card) {

        cardContainer.add(new CardBasic(card));
        totalCardCount++;
        updateTotalCardCountText();
    }

    /**
     * Removes a card from the deck.
     *
     * @param card CardDTO object.
     */
    public void removeCard(CardDTO card) {
        cardContainer.getChildren()
                .filter(component -> component instanceof CardBasic)
                .map(component -> (CardBasic) component)
                .filter(cd -> cd.getCardId() == card.id())
                .findFirst()
                .ifPresent(component -> {
                    cardContainer.remove(component);
                    totalCardCount--;
                    updateTotalCardCountText();
                });
    }


    /**
     * Updates the total card count text.
     */
    private void updateTotalCardCountText() {
        totalCardCountText.setText("Total cards: " + totalCardCount + " / " + MAX_CARDS);
        if (totalCardCount > MAX_CARDS) {
            totalCardCountText.getElement().getStyle().set("color", "red");
        } else {
            totalCardCountText.getElement().getStyle().remove("color");
        }
    }

    /**
     * Getter for the cards in the deck.
     *
     * @return List of CardDTO objects.
     */
    public List<CardDTO> getCards() {
        return cardContainer.getChildren()
                .filter(component -> component instanceof CardBasic)
                .map(component -> (CardBasic) component)
                .map(CardBasic::getCard)
                .toList();
    }

    /**
     * Setter for the deck title.
     * @param name String object.
     */
    public void setName(String name) {
        deckTitle.setText(name);
    }
}