package com.example.starter.base;

import com.example.starter.base.dto.UserDTO;
import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Style;

/**
 * The HomePageContent class is a VerticalLayout that displays the content of the home page.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class HomePageContent extends VerticalLayout {

    public HomePageContent() {
        configureStyling();
        updateContent();
    }

    private void configureStyling() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("home-page-content");
        getStyle()
                .setBackground("linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)")
                .setPadding("2em");

    }

    public void updateContent() {
        removeAll();
        UserDTO user = UserHelper.getCurrentUser();

        Div card = createWelcomeCard(user);

        // Add features section if user is logged in
        if (user != null) {
            add(card, createFeaturesSection());
        } else {
            add(card);
        }
    }

    private Div createWelcomeCard(UserDTO user) {
        Div card = new Div();
        card.addClassName("welcome-card");
        card.getStyle()
                .setBackgroundColor("white")
                .setBorderRadius("15px")
                .setBoxShadow("0 4px 6px rgba(0, 0, 0, 0.1)")
                .setPadding("2em")
                .setTextAlign(Style.TextAlign.CENTER)
                .setMaxWidth("600px")
                .setWidth("100%");


        H1 title = new H1("PTCGPocketBuilder");
        title.getStyle()
                .setColor("#2c3e50")
                .setMargin("0 0 0.5em 0")
                .setFontSize("2.5em");


        H2 welcome = new H2(user == null ?
                "Welcome to PTCGPocketBuilder!" :
                "Welcome back, " + UserHelper.getCurrentUsername() + "!");
        welcome.getStyle()
                .setColor("#34495e")
                .setMargin("0 0 1em 0");

        Paragraph description = new Paragraph(user == null ?
                "Please log in to access the full functionality of our deck building tools." :
                "Ready to continue building your perfect deck?");
        description.getStyle()
                .setColor("#7f8c8d")
                .setLineHeight("1.6");


        card.add(title, welcome, description);
        return card;
    }

    private Div createFeaturesSection() {
        Div featuresSection = new Div();
        featuresSection.getStyle()
                .setMarginTop("2em")
                .setWidth("100%")
                .setMaxWidth("800px");


        HorizontalLayout featureCards = new HorizontalLayout();
        featureCards.setWidthFull();
        featureCards.setJustifyContentMode(JustifyContentMode.CENTER);
        featureCards.setSpacing(true);

        // Add feature cards
        featureCards.add(
                createFeatureCard("Build Decks", "Create and manage your Pokemon TCG decks"),
                createFeatureCard("Collection", "Manage your card collection"),
                createFeatureCard("Decks", "View the best decks from the community")
        );

        featuresSection.add(featureCards);
        return featuresSection;
    }

    private Div createFeatureCard(String title, String description) {
        Div card = new Div();
        card.addClassName("feature-card");
        card.getStyle()
                .setBackgroundColor("white")
                .setBorderRadius("10px")
                .setBoxShadow("0 2px 4px rgba(0, 0, 0, 0.1)")
                .setPadding("1.5em")
                .setTextAlign(Style.TextAlign.CENTER)
                .setWidth("200px")
                .setMargin("0.5em");


        H3 headerTitle = new H3(title);
        headerTitle.getStyle()
                .setColor("#2c3e50")
                .setMargin("0 0 0.5em 0");


        Paragraph desc = new Paragraph(description);
        desc.getStyle()
                .setColor("#7f8c8d")
                .setMargin("0")
                .setFontSize("0.9em");


        card.add(headerTitle, desc);
        return card;
    }
}
