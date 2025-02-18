package com.example.starter.base.views;

import com.example.starter.base.HomePageContent;
import com.example.starter.base.MenuBar;
import com.example.starter.base.utilities.Constants;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import static com.example.starter.base.utilities.Constants.LOGIN_NOTIFICATION_KEY;

/**
 * The main view contains a button and a click listener.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_HOME)
public class MainView extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {


    private final HomePageContent contentArea;

    public MainView() {

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Create components
        contentArea = new HomePageContent();
        MenuBar menuBar = new MenuBar();

        // Create split layout
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(contentArea);
        splitLayout.setSplitterPosition(75);

        // Assemble the layout
        add(menuBar, splitLayout);
        expand(splitLayout);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        contentArea.updateContent();
    }

    /**
     * Method that is called after the view is navigated to. Shows a notification if the user is not logged in.
     *
     * @param event the after navigation event
     */
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Boolean showNotification = (Boolean) VaadinSession.getCurrent().getAttribute(LOGIN_NOTIFICATION_KEY);

        if (showNotification != null && showNotification) {
            Notification.show("You must be logged in to use this feature!", 3000, Notification.Position.MIDDLE);
            VaadinSession.getCurrent().setAttribute(LOGIN_NOTIFICATION_KEY, null);
            UI.getCurrent().navigate(Constants.ROUTE_HOME); //Must be otherwise the url is not updated...
        }

    }
}