package com.example.starter.base;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import org.jboss.logging.Logger;

/**
 * Class that listens to the startup and shutdown events to send emails when a JMS message is received.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class MailSender {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    Mailer mailer;

    JMSContext context;

    private static final Logger LOG = Logger.getLogger(MailSender.class);

    /**
     * Listen to the startup event to create the JMS context and add a message listener to send emails.
     *
     * @param ev the startup event
     */
    void onStart(@Observes StartupEvent ev) {
        LOG.debug("StartupEvent listened submitting this to run in thread");
        context = connectionFactory.createContext();

        try {
            LOG.debug("MailSender is running");
            var queue = context.createQueue("queue");
            var consumer = context.createConsumer(queue);

            consumer.setMessageListener(message -> {
                try {
                    String email = message.getStringProperty("to");
                    String subject = message.getStringProperty("action") + " for one of your deck";
                    String body = "Your deck : " + message.getStringProperty("deck")
                            + " has been rated by the user : " + message.getStringProperty("rater") + "\n"
                            + message.getStringProperty("action") + " : " + message.getStringProperty("rating");
                    LOG.debug("email to : " + email);
                    LOG.debug("subject : " + subject);
                    LOG.debug("mail body : " + body);

                    mailer.send(
                            Mail.withText(email, subject, body)
                    );
                } catch (Exception e) {
                    LOG.error("Couldn't send email : " + e.getMessage());
                }
            });
        } catch (Exception e) {
            LOG.error("Couldn't create context : " + e.getMessage());
        }
    }

    /**
     * Listen to the shutdown event to close the context and remove the JMS message listener.
     *
     * @param ev the shutdown event
     */
    void onStop(@Observes ShutdownEvent ev) {
        LOG.debug("ShutdownEvent called");
        context.close();
    }
}
