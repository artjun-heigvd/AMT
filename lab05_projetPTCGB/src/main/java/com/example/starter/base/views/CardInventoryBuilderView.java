package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.service.InventoryService;
import com.example.starter.base.service.UserService;
import com.example.starter.base.utilities.KeyboardUtils;
import com.example.starter.base.utilities.cards.CardInventoryDisplayer;
import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.UserHelper;
import com.example.starter.base.utilities.cards.CardWithCounter;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;


/**
 * View for the inventory builder
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_INVENTORY)

public class CardInventoryBuilderView extends AuthorizedView implements KeyNotifier {



    /**
     * The inventory service
     */
    @Inject
    InventoryService inventoryService;

    /**
     * The user service
     */
    @Inject
    UserService userService;
    FlexLayout cardContainer;




    /**
     * Constructor for the CardInventoryBuilderView.
     */

    public CardInventoryBuilderView() {
        setLayout();
        getElement().setAttribute("tabindex", "0");
        addAttachListener(e -> getElement().executeJs("this.focus()"));
    }

    @PostConstruct
    private void init() {
        cardContainer = (FlexLayout) getComponentAt(1);


        // Add keyboard navigation
        KeyboardUtils keyboardUtils = new KeyboardUtils(cardContainer,  this, this);

        var cardCountMap = inventoryService.getCardCountForAllCardsByUserId(UserHelper.getCurrentId());

        for (var card : cardCountMap.entrySet()) {
            var newCard = getCardInventoryDisplayer(card.getKey(), card.getValue());
            cardContainer.add(newCard);
        }
        if (cardContainer.getComponentCount() > 0) {
            getElement().getNode().runWhenAttached(ui ->
                    ui.beforeClientResponse(this, context ->
                        keyboardUtils.selectCard((CardWithCounter) cardContainer.getComponentAt(0))
                    )
            );
        }


    }


    /**
     * Returns a CardInventoryDisplayer object.
     *
     * @param card            the card to display
     * @param count           the count of the card
     * @return a CardInventoryDisplayer object
     */
    private CardInventoryDisplayer getCardInventoryDisplayer(CardDTO card, Integer count) {
        var newCard = new CardInventoryDisplayer(card, count, (event) -> {
            CardInventoryDisplayer source = (CardInventoryDisplayer) event.getSource();
            Notification.show("Card " + source.getCard().name() + " added to inventory");
            userService.addCard(UserHelper.getCurrentId(), source.getCard().id());
        }, (event) -> {
            CardInventoryDisplayer source = (CardInventoryDisplayer) event.getSource();

            userService.removeCard(UserHelper.getCurrentId(), source.getCard().id());
            Notification.show("Card " + source.getCard().name() + " removed from inventory");
        });
        if (count > 0) {
            newCard.highlightCard();
        }
        return newCard;
    }

    /**
     * Sets the layout of the view.
     */
    private void setLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        MenuBar menuBar = new MenuBar();

        FlexLayout cardContainer = new FlexLayout();
        cardContainer.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexWrap(Style.FlexWrap.WRAP)
                .setWidth("100%")
                .setMaxWidth("100vw")
                .setPadding("16px") // Internal padding for content
                .setMargin("0 auto") // Centered alignment without overflow
                .set("gap", "16px")
                .set("box-sizing", "border-box");


        this.getStyle()
                .setWidth("100%")
                .setMaxWidth("100vw"); // Limit width to the viewport


        // Create controls info panel
        Div controlsInfo = createControlsInfoPanel();

        add(menuBar, cardContainer, controlsInfo);
    }

    private Div createControlsInfoPanel() {
        Div controlsInfo = new Div();
        // Main container styling
        controlsInfo.getStyle()
                .setBackgroundColor("#333")
                .setBorderRadius("15px")
                .setPadding("20px")
                .setPosition(Style.Position.FIXED)
                .setTop("80px")
                .setRight("20px")
                .setWidth("250px")
                .setBoxShadow("0 4px 15px rgba(0, 0, 0, 0.2)")
                .setColor("white")
                .setZIndex(1000)
                .setBorder("1px solid rgba(255, 255, 255, 0.1)")
                .set("backdrop-filter", "blur(10px)");

        // Header with title and close button
        Div header = new Div();
        header.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setJustifyContent(Style.JustifyContent.SPACE_BETWEEN)
                .setAlignItems(Style.AlignItems.CENTER)
                .setMarginBottom("15px")
                .setPaddingBottom("10px")
                .setBorderBottom("2px solid rgba(255, 255, 255, 0.2)");

        // Title
        Div title = new Div();
        title.setText("Controls Guide");
        title.getStyle()
                .setFontSize("1.2em")
                .setFontWeight(Style.FontWeight.BOLD);


        // Close button
        Button closeButton = new Button("×");
        closeButton.getStyle()
                .setBackground("none")
                .setBorder("none")
                .setColor("white")
                .setFontSize("20px")
                .setCursor("pointer")
                .setPadding("0")
                .setWidth("24px")
                .setHeight("24px")
                .setDisplay(Style.Display.FLEX)
                .setAlignItems(Style.AlignItems.CENTER)
                .setJustifyContent(Style.JustifyContent.CENTER)
                .setBorderRadius("50%")
                .setTransition("background-color 0.3s");


        closeButton.addClickListener(e -> controlsInfo.removeFromParent());

        header.add(title, closeButton);

        // Controls list
        Div controlsList = new Div();
        controlsList.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setFlexDirection(Style.FlexDirection.COLUMN)
                .set("gap", "12px");

        // Individual control items
        String[][] controls = {
                {"↑↓←→", "Navigate Cards"},
                {"INSERT", "Add Card"},
                {"DELETE", "Remove Card"}
        };

        for (String[] control : controls) {
            Div controlItem = new Div();
            controlItem.getStyle()
                    .setDisplay(Style.Display.FLEX)
                    .setAlignItems(Style.AlignItems.CENTER)
                    .set("gap", "10px");

            Div key = new Div();
            key.setText(control[0]);
            key.getStyle()
                    .setBackground("rgba(255, 255, 255, 0.1)")
                    .setPadding("5px 10px")
                    .setBorderRadius("8px")
                    .set("font-family", "monospace")
                    .setFontWeight(Style.FontWeight.BOLD)
                    .setFontSize("0.9em")
                    .setMinWidth("70px")
                    .setTextAlign(Style.TextAlign.CENTER)
                    .setBorder("1px solid rgba(255, 255, 255, 0.2)");


            Div action = new Div();
            action.setText(control[1]);
            action.getStyle()
                    .setFontSize("0.9em")
                    .setOpacity("0.9");

            controlItem.add(key, action);
            controlsList.add(controlItem);
        }

        // Add hover effect
        controlsInfo.getElement().addEventListener("mouseenter", e ->
                controlsInfo.getStyle()
                        .setTransform("translateY(-5px)")
                        .setBoxShadow("0 6px 20px rgba(0, 0, 0, 0.25)")
        );

        controlsInfo.getElement().addEventListener("mouseleave", e ->
                controlsInfo.getStyle()
                        .setTransform("translateY(0)")
                        .setBoxShadow("0 4px 15px rgba(0, 0, 0, 0.2)")

        );

        // Add transition for smooth hover effect
        controlsInfo.getStyle()
                .set("transition", "transform 0.3s ease, 0.3s ease");

        controlsInfo.add(header, controlsList);

        return controlsInfo;
    }



}
