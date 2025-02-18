package com.example.starter.base.utilities.decks;

import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.utilities.ListDisplayerHelper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


/**
 * DeckDisplayer is a class that extends VerticalLayout.
 * It displays the deck image and name.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class DeckDisplayer extends VerticalLayout {

    /**
     * Constructor for DeckDisplayer.
     *
     * @param deck DeckDTO object.
     * @param deckClickListener ComponentEventListener object.
     */
    public DeckDisplayer(DeckDTO deck, ComponentEventListener<ClickEvent<VerticalLayout>> deckClickListener) {
        ListDisplayerHelper.setVerticalLayout(this);


        Image deckImage = new Image("images/img_1.png", "Deck Image");
        Text deckName = new Text(deck.name());
        deckImage.setWidth("11vw");
        deckImage.getStyle()
                .set("object-fit", "contain")
                .setTransform("scale(0.9)");

        this.add(deckImage, deckName);

        this.addClickListener(deckClickListener);
    }
}
