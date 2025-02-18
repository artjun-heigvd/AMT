package com.example.starter.base.views;

import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;

import static com.example.starter.base.utilities.Constants.LOGIN_NOTIFICATION_KEY;

/**
 * Abstract class representing an authorized view.
 * Ensures that the user is logged in before accessing the view.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public abstract class AuthorizedView extends VerticalLayout implements BeforeEnterObserver {

    /**
     * Called before the navigation target is entered.
     * Checks if the user is logged in.
     *
     * @param event the before enter event
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        checkLoggedIn(event);
    }

    /**
     * Checks if the user is logged in.
     * If the user is not logged in, forwards to the main view.
     *
     * @param event the before enter event
     */
    public void checkLoggedIn(BeforeEnterEvent event) {

        if (!UserHelper.isLoggedIn()) {
            VaadinSession.getCurrent().setAttribute(LOGIN_NOTIFICATION_KEY, true);
            event.forwardTo(MainView.class); // Can't use UI navigateTo because it's not a UI object yet
        }
    }

}
