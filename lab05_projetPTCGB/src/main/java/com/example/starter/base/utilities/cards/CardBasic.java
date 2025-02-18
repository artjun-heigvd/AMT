package com.example.starter.base.utilities.cards;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.utilities.ListDisplayerHelper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * CardBasic is a class that extends VerticalLayout.
 * It displays the card image and name.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class CardBasic extends VerticalLayout {

    /**
     * The card object.
     */
    protected final CardDTO card;

    /**
     * The card image.
     */
    protected Image cardImage;

    /**
     * Getter for the card id.
     *
     * @return Integer object.
     */
    public int getCardId() {
        return card.id();
    }

    /**
     * Getter for the card image.
     *
     * @return Image object.
     */
    public Image getCardImage() {
        return cardImage;
    }
    /**
     * Getter for the card.
     *
     * @return CardDTO object.
     */
    public CardDTO getCard() {
        return card;
    }

    /**
     * Constructor for CardBasic.
     *
     * @param card CardDTO object.
     */
    public CardBasic(CardDTO card) {
        ListDisplayerHelper.setVerticalLayout(this);
        this.card = card;
        this.cardImage = getImage(card);
        Text cardNameField = new Text(card.name());
        this.add(cardImage, cardNameField);
    }

    private static Image getImage(CardDTO card) {
        var res = new Image(card.image(), card.name());
        res.setWidth("11vw");
        res.getStyle()
                .setMaxWidth("100%")
                .set("object-fit", "contain")
                .setTransform("scale(0.9)");

        return res;
    }

}