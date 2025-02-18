package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.service.DeckService;
import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.decks.DeckDisplayer;
import com.example.starter.base.utilities.StarRating;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.util.List;

/**
 * View for all decks
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_ALL_DECKS)
public class AllDeckView extends VerticalLayout {


    @Inject
    DeckService deckService;

    /**
     * Constructor for the AllDeckView.
     */
    public AllDeckView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    /**
     * Initializes the view.
     */
    @PostConstruct
    private void init() {

        MenuBar menuBar = new MenuBar();

        // Create a flex layout container for the decks
        FlexLayout cardContainer = new FlexLayout();
        cardContainer.setWidth("100%");
        cardContainer.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexWrap(Style.FlexWrap.WRAP)
                .setMargin("16px")
                .setJustifyContent(Style.JustifyContent.FLEX_START)
                .set("gap", "16px");


        // Add cards to the container
        List<DeckDTO> decksDTO = deckService.getDecksOrderedByRating();

        for (DeckDTO deck : decksDTO) {
            var deckDisplay = new DeckDisplayer(deck, event ->
                getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_RATING.replace(":deckId", String.valueOf(deck.id()))))
            );

            Span deckUsername = new Span("by " + deckService.getCreator(deck.id()).username());
            StarRating deckRating = new StarRating();
            deckRating.setRating(deckService.getAverageRating(deck.id()));
            deckRating.setReadOnly(true);
            deckRating.removeText();

            deckDisplay.add(deckUsername, deckRating);
            cardContainer.add(deckDisplay);
        }


        add(menuBar, cardContainer);
    }
}
