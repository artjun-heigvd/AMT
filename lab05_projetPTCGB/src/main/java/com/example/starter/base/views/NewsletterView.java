package com.example.starter.base.views;

import com.example.starter.base.utilities.Constants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.inject.Inject;

import java.util.List;

/**
 * View that allows sending a newsletter to some subscribers when a button is clicked.
 * It is mostly made for demonstration and testing purposes and does not have a real use case in the application.
 * The view allows to make sure that the mailer is correctly configured and that the application can send emails.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_NEWSLETTER)
public class NewsletterView extends VerticalLayout {

    @Inject
    Mailer mailer;

    /**
     * Constructor for the NewsletterView.
     */
    public NewsletterView() {
        add(new com.vaadin.flow.component.html.H1("Pokémon TCG Builder Newsletter"));
        Button sendNewsletterButton = new Button("Send Newsletter", event -> sendNewsletter());
        add(sendNewsletterButton);
    }

    /**
     * Sends a newsletter to some subscribers.
     */
    private void sendNewsletter() {
        try {
            Mail mail = new Mail();
            mail.setFrom("ptcgbuilder@gmail.com");
            mail.setTo(List.of("trainer1@pokemail.com", "trainer2@pokemail.com"));
            mail.setSubject("PTCG Builder: Latest Updates & Tips");
            mail.setText(
                    "Hello Trainer,\n\n" +
                            "Here's what's new in Pokémon Trading Card Game Builder:\n\n" +
                            "- New card sets added: Scarlet & Violet - Paldea Evolved\n" +
                            "- Deck optimization tips for 2024\n" +
                            "- Upcoming tournaments and events\n\n" +
                            "Visit PTCG Builder to explore more!\n\n" +
                            "Happy battling,\n" +
                            "The PTCG Builder Team"
            );
            mailer.send(mail);
            Notification.show("Newsletter sent successfully!", 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Failed to send newsletter: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }
}
