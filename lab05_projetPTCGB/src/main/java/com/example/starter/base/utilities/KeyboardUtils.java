package com.example.starter.base.utilities;

import com.example.starter.base.utilities.cards.CardInventoryDisplayer;
import com.example.starter.base.utilities.cards.CardWithCounter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Utility Class to handle KeyBoardPress
 */
public class KeyboardUtils {

    CardWithCounter currentlySelectedCard;
    FlexLayout cardContainer;
    int oldCardPerRow = 0;

    /**
     * Constructor for KeyboardUtils.
     * @param cardContainer FlexLayout containing CardWithCounter objects.
     * @param keyNotifier KeyNotifier object.
     */
    public KeyboardUtils(FlexLayout cardContainer, KeyNotifier keyNotifier, VerticalLayout layout) {
        this.cardContainer = cardContainer;
        setKeyboardShortcuts(keyNotifier);
    }

    /**
     * Sets the keyboard shortcuts for the inventory builder.
     * @param keyNotifier keyNotifier object.
     */
    public void setKeyboardShortcuts(KeyNotifier keyNotifier) {
        keyNotifier.addKeyDownListener(Key.ARROW_RIGHT, e -> this.handleRightArrow());
        keyNotifier.addKeyDownListener(Key.ARROW_LEFT, e -> this.handleLeftArrow());
        keyNotifier.addKeyDownListener(Key.ARROW_UP, e -> this.handleUpArrow());
        keyNotifier.addKeyDownListener(Key.ARROW_DOWN, e -> this.handleDownArrow());
        keyNotifier.addKeyDownListener(Key.INSERT, e -> this.handleAddCard());
        keyNotifier.addKeyDownListener(Key.DELETE, e -> this.handleRemoveCard());

    }

    /**
     * Handles the right arrow key press. Moves the selection to the card to the right of the currently selected card.
     */
    private void handleRightArrow() {
        if (currentlySelectedCard != null) {
            int currentIndex = cardContainer.indexOf(currentlySelectedCard);
            if (currentIndex < cardContainer.getComponentCount() - 1) {
                navigateToCard(currentIndex + 1);
            }
        }
    }

    /**
     * Handles the left arrow key press. Moves the selection to the card to the left of the currently selected card.
     */
    private void handleLeftArrow() {
        if (currentlySelectedCard != null) {
            int currentIndex = cardContainer.indexOf(currentlySelectedCard);
            if (currentIndex > 0) {
                navigateToCard(currentIndex - 1);
            }
        }
    }

    /**
     * Handles the up arrow key press. Moves the selection to the card above the currently selected card.
     */
    private void handleUpArrow() {
        if (currentlySelectedCard != null) {
            int currentIndex = cardContainer.indexOf(currentlySelectedCard);
            int cardsPerRow = getCardsPerRow();
            if (currentIndex >= cardsPerRow) {
                navigateToCard(currentIndex - cardsPerRow);
            }
        }
    }

    /**
     * Handles the down arrow key press. Moves the selection to the card below the currently selected card.
     */
    private void handleDownArrow() {
        if (currentlySelectedCard != null) {
            int currentIndex = cardContainer.indexOf(currentlySelectedCard);
            int cardsPerRow = getCardsPerRow(); // Adjust based on your layout
            if (currentIndex + cardsPerRow < cardContainer.getComponentCount()) {
                navigateToCard(currentIndex + cardsPerRow);
            }
        }
    }



    /**
     * Navigates to the card at the specified index.
     * @param index the index of the card to navigate to.
     */
    private void navigateToCard(int index) {
        CardInventoryDisplayer targetCard = (CardInventoryDisplayer) cardContainer.getComponentAt(index);
        selectCard(targetCard);
    }
    /**
     * Selects the specified card.
     * @param card the card to select.
     */
    public void selectCard(CardWithCounter card) {
        if (currentlySelectedCard != null) {
            deselectCard(currentlySelectedCard);
        }
        currentlySelectedCard = card;
        card.getElement().setAttribute("tabindex", "0");
        card.getElement().executeJs("this.focus()");
        card.getStyle()
                .set("transform", "scale(1.05)")
                .set("border", "2px solid #007bff")
                .set("box-shadow", "0 0 10px rgba(0,123,255,0.5)")
                .set("z-index", "1");
    }

    /**
     * Deselects the specified card.
     * @param card the card to deselect.
     */
    private void deselectCard(CardWithCounter card) {
        card.getStyle()
                .remove("transform")
                .remove("border")
                .remove("box-shadow")
                .remove("z-index")
                .setBorder("1px solid #ddd")
                .setBorderRadius("8px");
    }

    /**
     * Handles adding keyboard press to add a card.
     */
    private void handleAddCard() {
        if (currentlySelectedCard != null) {
            currentlySelectedCard.getPlusButton().click();
        }
    }

    /**
     * Handles adding keyboard press to remove a card.
     */
    private void handleRemoveCard() {
        if (currentlySelectedCard != null) {
            currentlySelectedCard.getMinusButton().click();
        }
    }

    /**
     * Gets the number of cards per row in the card container using JavaScript.
     * @return the number of cards per row.
     */
    public int getCardsPerRow() {
        if (cardContainer.getComponentCount() == 0) {
            return 0;
        }

        Component firstCard = cardContainer.getComponentAt(0);
        firstCard.getElement().executeJs("return this.getBoundingClientRect().width +" +
                " parseFloat(getComputedStyle(this).paddingRight) +" +
                "parseFloat(getComputedStyle(this).paddingRight) + parseFloat(getComputedStyle(this).paddingLeft) ").then(Double.class, cardWidth -> {
            cardContainer.getElement().executeJs("return this.getBoundingClientRect().width").then(Double.class, containerWidth -> {
                if (cardWidth != 0) {
                    oldCardPerRow = ((int) Math.floor(containerWidth / cardWidth));
                }
            });
        });

        return oldCardPerRow;
    }

}
