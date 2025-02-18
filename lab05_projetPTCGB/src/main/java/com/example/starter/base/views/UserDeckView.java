package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.DeckDTO;
import com.example.starter.base.service.DeckService;
import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.decks.DeckDisplayer;
import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
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
@Route(Constants.ROUTE_MY_DECKS)
public class UserDeckView extends AuthorizedView implements AfterNavigationObserver {

    /**
     * The deck service
     */
    @Inject
    DeckService deckService;
    /**
     * Constructor for the AllDeckView.
     */
    public UserDeckView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    /**
     * Method that is called after the view is entered. Check if the user has access to the deck and display a notification if not.
     * @param event the after navigation event
     */
    @Override
      public void afterNavigation(AfterNavigationEvent event) {
          if (UI.getCurrent().getSession().getAttribute(Constants.NO_ACCESS) != null) {
              Notification.show("You cannot edit this deck!", 3000, Notification.Position.MIDDLE);
              UI.getCurrent().getSession().setAttribute(Constants.NO_ACCESS, null);
          }
      }

    /**
     * Initializes the view.
     */
    @PostConstruct
    private void init() {

        int userId = UserHelper.getCurrentId();
        MenuBar menuBar = new MenuBar();


        // Create a flex layout container for the cards
        FlexLayout cardContainer = new FlexLayout();
        cardContainer.setWidth("100%");
        cardContainer.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexWrap(Style.FlexWrap.WRAP)
                .setMargin("16px")
                .setJustifyContent(Style.JustifyContent.FLEX_START);

        // Add cards to the container
        List<DeckDTO> decksDTO = deckService.getDecksByUserId(userId);

        for (DeckDTO deck : decksDTO) {
            cardContainer.add(new DeckDisplayer(deck, event ->
                getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_DECK_BUILDING.replace(":deckId", String.valueOf(deck.id()))))
            ));
        }

        FlexLayout button = new FlexLayout();
        button.setWidth("100%");
        button.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setJustifyContent(Style.JustifyContent.CENTER)
                .setAlignItems(Style.AlignItems.CENTER)
                .setPaddingTop("10dp");

        Button addNewDeckButton = new Button("Add a new Deck here !");
        // Add a new deck
        addNewDeckButton.addClickListener(ev -> {
            int newId = deckService.newDeck("New Deck", userId).id();

            getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_DECK_BUILDING.replace(":deckId", String.valueOf(newId))));
        });

        button.add(addNewDeckButton);

        add(menuBar, cardContainer, button);
    }
}