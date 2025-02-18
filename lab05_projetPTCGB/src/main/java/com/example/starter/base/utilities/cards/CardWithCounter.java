package com.example.starter.base.utilities.cards;


import com.example.starter.base.dto.CardDTO;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.function.Function;

/**
 * CardWithCounter is a class that extends CardBasic and adds a counter to the card.
 * The counter is used to keep track of how many of the card are selected.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class CardWithCounter extends CardBasic implements KeyNotifier {

    protected int selectedCount;
    protected final TextField selectedCountField;
    // Counter components
    protected Button minusButton;
    protected Button plusButton;
    /**
     * Function object that determines if a card can be added.
     */
    Function<CardDTO, Boolean> canAddCard = card -> true;

    /**
     * Function object that determines if a card can be removed.
     */
    Function<CardDTO, Boolean> canRemoveCard = card -> true;

    /**
     * Listener for adding a card.
     */
    private final ComponentEventListener<ClickEvent<VerticalLayout>> addListener;

    /**
     * Listener for removing a card.
     */
    private final ComponentEventListener<ClickEvent<VerticalLayout>> removeListener;


    /**
     * Removes one card from the selected count, updates the selected count field, and fires the remove listener.
     */
    public void remove() {
        --selectedCount;
        selectedCountField.setValue(String.valueOf(this.selectedCount));
        this.removeListener.onComponentEvent(new ClickEvent<>(this));
    }

    /**
     * Add one card from the selected count, updates the selected count field, and fires the add listener.
     */
    public void add() {
        ++selectedCount;
        selectedCountField.setValue(String.valueOf(this.selectedCount));
        this.addListener.onComponentEvent(new ClickEvent<>(this));

    }

    /**
     * Sets the canAddCard function.
     *
     * @param canAddCard Function object.
     */
    public void setCanAddCard(Function<CardDTO, Boolean> canAddCard) {
        this.canAddCard = canAddCard;
    }

    /**
     * Sets the canRemoveCard function.
     *
     * @param canRemoveCard Function object.
     */
    public void setCanRemoveCard(Function<CardDTO, Boolean> canRemoveCard) {
        this.canRemoveCard = canRemoveCard;
    }

    /**
     * Constructor for CardWithCounter.
     *
     * @param card           CardDTO object.
     * @param selectedCount  Integer object.
     * @param addListener    ComponentEventListener object.
     * @param removeListener ComponentEventListener object.
     */
    public CardWithCounter(CardDTO card, Integer selectedCount,
                           ComponentEventListener<ClickEvent<VerticalLayout>> addListener,
                           ComponentEventListener<ClickEvent<VerticalLayout>> removeListener) {
        super(card);
        this.selectedCount = selectedCount != null ? selectedCount : 0;
        this.addListener = addListener;
        this.removeListener = removeListener;

        // Counter components
        minusButton = new Button("-");
        plusButton = new Button("+");


        selectedCountField = new TextField();
        selectedCountField.setValue(String.valueOf(selectedCount));
        selectedCountField.setReadOnly(true);
        selectedCountField.setWidth("40px");


        minusButton.addClickListener(e -> {
            if (this.selectedCount > 0 && canRemoveCard.apply(card)) {
                remove();
            }
        });

        plusButton.addClickListener(e -> {
            if (this.selectedCount < Integer.MAX_VALUE && canAddCard.apply(card)) {
                add();
            }
        });

        setupLayout();
    }


    /**
     * Sets up the layout for the card with counter.
     */
    private void setupLayout() {
        HorizontalLayout counterLayout = new HorizontalLayout();
        counterLayout.setSpacing(false);
        counterLayout.setAlignItems(Alignment.CENTER);
        counterLayout.add(minusButton, selectedCountField, plusButton);


        this.add(counterLayout);
    }

    /**
     * Getter for minusButton.
     *
     * @return Button object.
     */
    public Button getMinusButton() {
        return minusButton;
    }

    /**
     * Getter for plusButton.
     *
     * @return Button object.
     */
    public Button getPlusButton() {
        return plusButton;
    }
}

