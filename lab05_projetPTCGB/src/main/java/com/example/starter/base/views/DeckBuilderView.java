package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.entity.Type;
import com.example.starter.base.service.DeckService;
import com.example.starter.base.service.InventoryService;
import com.example.starter.base.utilities.*;
import com.example.starter.base.utilities.cards.CardWithCounter;
import com.example.starter.base.utilities.cards.CardWithTotalOwned;
import com.example.starter.base.utilities.decks.DeckContent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;

import java.util.Map;


/** View for the deck builder
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_DECK_BUILDING)
public class DeckBuilderView extends AuthorizedView {

    // Constants
    private static final int MAX_CARDS_IN_DECK = 20;
    private static final int MAX_SAME_CARD_IN_DECK = 2;

    @Inject
    DeckService deckService;

    @Inject
    InventoryService inventoryService;

    private DeckContent deckContent;
    private Map<CardDTO, Integer> inventoryCards;
    private Map<CardDTO, Integer> deckCards;
    private TextField deckNameField;
    private ComboBox<Type> deckTypeComboBox;

    /**
     * Method that is called before the view is entered. Get the deck id from the route parameters and initialize the view.
     *
     * @param event the before enter event
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        super.beforeEnter(event);



        int deckId = Integer.parseInt(event.getRouteParameters().get("deckId").orElse("1"));
        var deck = deckService.getDeckById(deckId);
        // check if the user has access to the deck
        if (deck.userId() != UserHelper.getCurrentId()) {
            VaadinSession.getCurrent().setAttribute(Constants.NO_ACCESS, true);
            event.forwardTo(Constants.ROUTE_MY_DECKS);
            return;
        }
        deckContent = new DeckContent(deck, deckService.getDeckCards(deckId));
        inventoryCards = inventoryService.getCardCountByUserId(UserHelper.getCurrentId());
        deckCards = deckService.getCardCount(deckId);

        setupMainLayout(deckId, deck.name(), deck.type());
    }

    /**
     * Set up the main layout
     * @param deckId the deck id
     * @param deckName the deck name
     * @param deckType the deck type
     */
    private void setupMainLayout(int deckId, String deckName, String deckType) {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        SplitLayout mainLayout = new SplitLayout();
        mainLayout.setSizeFull();
        mainLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        mainLayout.addToPrimary(createInventoryPanel());
        mainLayout.addToSecondary(createDeckPanel(deckId, deckName, Type.fromString(deckType)));
        mainLayout.setSplitterPosition(40);

        add(new MenuBar(), mainLayout);
        expand(mainLayout);
    }
    /**
     * Create the inventory panel
     * @return the inventory panel
     */
    private VerticalLayout createInventoryPanel() {
        FlexLayout cardContainer = new FlexLayout();
        cardContainer.setSizeFull();
        cardContainer.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexWrap(Style.FlexWrap.WRAP)
                .setMargin("16px")
                .setPadding("16px")
                .setJustifyContent(Style.JustifyContent.FLEX_START)
                .set("gap", "16px");


        inventoryCards.forEach((card, availableCount) -> {
            CardWithTotalOwned cardDisplayer = new CardWithTotalOwned(
                    card,
                    deckCards.getOrDefault(card, 0),
                    availableCount,
                    event -> handleAddCard((CardWithCounter) event.getSource()),
                    event -> handleRemoveCard((CardWithCounter) event.getSource())
            );

            cardDisplayer.setCanAddCard(c -> {
                if (deckContent.getCards().size() >= MAX_CARDS_IN_DECK) {
                    Notification.show("Maximum deck size reached (" + MAX_CARDS_IN_DECK + ")");
                    return false;
                }
                if (deckCards.getOrDefault(c, 0) >= MAX_SAME_CARD_IN_DECK) {
                    Notification.show("Maximum copies of this card reached (" + MAX_SAME_CARD_IN_DECK + ")");
                    return false;
                }
                return true;
            });
            cardDisplayer.setCanRemoveCard(c -> deckCards.getOrDefault(c, 0) > 0);

            cardContainer.add(cardDisplayer);
        });

        VerticalLayout inventoryLayout = new VerticalLayout();
        inventoryLayout.setPadding(true);
        inventoryLayout.setSpacing(true);
        inventoryLayout.add(
                new H3("Your Card Inventory"),
                new Paragraph("Click on cards to add them to your deck →"),
                cardContainer
        );
        return inventoryLayout;
    }

    /**
     * Create the deck panel
     * @param deckId the deck id
     * @param deckName the deck name
     * @param deckType the deck type
     * @return the deck panel
     */
    private VerticalLayout createDeckPanel(int deckId, String deckName, Type deckType) {
        deckNameField = new TextField("Deck Name");
        deckNameField.setValue(deckName);

        deckTypeComboBox = new ComboBox<>("Deck Type");
        deckTypeComboBox.setItems(Type.values());
        deckTypeComboBox.setItemLabelGenerator(Type::toString);
        deckTypeComboBox.setValue(deckType);

        Button saveButton = new Button("Save Deck", e -> {
            String newDeckName = deckNameField.getValue();
            Type selectedDeckType = deckTypeComboBox.getValue();
            deckService.updateInfos(deckId, selectedDeckType.toString(), deckContent.getCards());
            deckService.updateName(deckId, newDeckName);
            deckContent.setName(newDeckName);
            Notification.show("Deck saved successfully with name: " + newDeckName + " and type: " + selectedDeckType);
        });

        VerticalLayout deckLayout = new VerticalLayout();
        deckLayout.setPadding(true);
        deckLayout.setSpacing(true);
        deckLayout.add(deckNameField, deckTypeComboBox, deckContent, saveButton);
        return deckLayout;
    }

    /**
     * Handle adding a card to the deck
     * @param source the card to add
     */
    private void handleAddCard (CardWithCounter source){
        CardDTO card = source.getCard();
        deckContent.addCard(card);
        deckCards.merge(card, 1, Integer::sum);
    }

    /**
     * Handle removing a card from the deck
     * @param source the card to remove
     */
    private void handleRemoveCard (CardWithCounter source){
        CardDTO card = source.getCard();
        deckContent.removeCard(card);
        deckCards.merge(card, -1, Integer::sum);
    }

}
