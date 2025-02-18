package com.example.starter.base;

import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The menu bar for the application
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class MenuBar extends HorizontalLayout {
    private final Button homeButton;
    private final Button inventoryButton;
    private final Button deckButton;
    private final Button loginButton;
    private final Button allDecksButton;
    private final Button logoutButton;

    /**
     * Constructor for the menu bar
     */
    public MenuBar() {
        setWidthFull();
        getStyle()
                .setBackgroundColor("#333")
                .setColor("white")
                .setPadding("10px");


        // Create buttons
        homeButton = new Button("Home");
        homeButton.getStyle().setColor("white");

        inventoryButton = new Button("Inventory");
        inventoryButton.getStyle().setColor("white");

        deckButton = new Button("My decks");
        deckButton.getStyle().setColor("white");

        allDecksButton = new Button("See all decks");
        allDecksButton.getStyle().setColor("white");

        logoutButton = new Button("Logout");
        logoutButton.getStyle().setColor("white");

        loginButton = new Button("Login");
        loginButton.getStyle().setColor("white");


        // Add click listeners
        logoutButton.addClickListener(buttonClickEvent -> {
            UserHelper.logout();
            logoutButton.getUI().ifPresent(ui -> {
                ui.navigate(Constants.ROUTE_HOME);
                ui.getPage().reload();
            });
        });

        loginButton.addClickListener(buttonClickEvent -> {
            loginButton.getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_LOGIN));
        });

        homeButton.addClickListener( buttonClickEvent -> {
           homeButton.getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_HOME));
        });

        inventoryButton.addClickListener( buttonClickEvent -> {
           inventoryButton.getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_INVENTORY));
        });

        deckButton.addClickListener( buttonClickEvent -> {
            deckButton.getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_MY_DECKS));
        });

        allDecksButton.addClickListener( buttonClickEvent -> {
            allDecksButton.getUI().ifPresent(ui -> ui.navigate(Constants.ROUTE_ALL_DECKS));
        });

        // Different display of buttons for logged in and logged out users
        if(UserHelper.isLoggedIn()) {
            add(homeButton, inventoryButton, deckButton, allDecksButton, logoutButton);
        } else {
            add(homeButton, loginButton, allDecksButton);
        }
    }
}
