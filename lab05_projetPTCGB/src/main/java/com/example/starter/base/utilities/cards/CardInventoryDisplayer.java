package com.example.starter.base.utilities.cards;

import com.example.starter.base.dto.CardDTO;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * CardInventoryDisplayer is a class that extends CardWithCounter and is used to display cards in the inventory.
 * It is used to display the card image, name, and count of the card in the inventory.
 * It also allows the user to add or remove card from the inventory.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class CardInventoryDisplayer extends CardWithCounter {


    /**
     * Getter for selectedCount.
     *
     * @return Integer object.
     */
    private boolean isSelected() {
        return this.selectedCount > 0;
    }

    /**
     * Constructor for CardInventoryDisplayer.
     *
     * @param card           CardDTO object.
     * @param count          Integer object.
     * @param addListener    ComponentEventListener object.
     * @param removeListener ComponentEventListener object.
     */
    public CardInventoryDisplayer(CardDTO card, Integer count, ComponentEventListener<ClickEvent<VerticalLayout>> addListener, ComponentEventListener<ClickEvent<VerticalLayout>> removeListener) {
        super(card, count, addListener, removeListener);

        this.getStyle().set("filter", "brightness(75%) saturate(140%)");


        highlightCard();
        // Add highlighting if at least one card is selected
        minusButton.addClickListener(e -> {
            highlightCard();
        });
        plusButton.addClickListener(e -> {
            highlightCard();
        });

    }

    /**
     * Highlights the card when it is selected.
     */
    public void highlightCard() {
        if (isSelected()) {
            this.getStyle()
                    .setBackgroundColor("rgba(0, 123, 255, 0.1)")
                    .set("filter", "brightness(100%) saturate(100%)");
            this.cardImage.getStyle().set("transform", "scale(1)");
        } else {
            this.getStyle()
                    .setBackgroundColor("transparent")
                    .set("filter", "brightness(75%) saturate(140%)");
            this.cardImage.getStyle().set("transform", "scale(0.9)");
        }
    }
}