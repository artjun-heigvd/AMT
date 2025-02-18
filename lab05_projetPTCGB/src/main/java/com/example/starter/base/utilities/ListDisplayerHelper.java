package com.example.starter.base.utilities;

import com.example.starter.base.dto.CardDTO;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;

/**
 * ListDisplayerHelper is a class that contains helper methods for displaying lists.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 * @see CardDTO
 */
public class ListDisplayerHelper {

    /**
     * Sets the layout of a card.
     *
     * @param vl VerticalLayout object.
     */
    public static void setVerticalLayout(VerticalLayout vl) {
        vl.setSpacing(false);
        vl.setPadding(false);
        vl.setAlignItems(FlexComponent.Alignment.CENTER);
        vl.getStyle()
                .setBorder("1px solid #ddd")
                .setBorderRadius("8px")
                .setPadding("8px")
                .setWidth("fit-content")
                .setHeight("fit-content")
                .setBoxSizing(Style.BoxSizing.BORDER_BOX)
                .setCursor("pointer");

    }

}

