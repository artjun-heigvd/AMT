package com.example.starter.base.utilities.cards;

import com.example.starter.base.dto.CardDTO;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * CardWithDeck is a class that extends CardWithCounter.
 * It adds a counter to show total owned cards.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 * @see CardWithCounter
 */
public class CardWithTotalOwned extends CardWithCounter {


    /**
     * Constructor for CardWithDeck.
     *
     * @param card           CardDTO object.
     * @param selected       Long object.
     * @param count          Long object.
     * @param addListener    ComponentEventListener object.
     * @param removeListener ComponentEventListener object.
     */
    public CardWithTotalOwned(CardDTO card, Integer selected, Integer count, ComponentEventListener<ClickEvent<VerticalLayout>> addListener, ComponentEventListener<ClickEvent<VerticalLayout>> removeListener) {
        super(card, selected, addListener, removeListener);
        Text countField = new Text("Total owned: " + count);
        this.count = count;
        this.add(countField);
    }

    private final Integer count;

    /**
     * Adds a card to the selected count if the selected count is less than the total count.
     * Calls the add method from CardWithCounter.
     */
    @Override
    public void add() {
        if (selectedCount < count) {
            super.add();
        }

    }
}