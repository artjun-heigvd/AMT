package ch.heigvd.amt.queue;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;

@ApplicationScoped
public class QueueConsumerA {

    @Inject
    ConnectionFactory connectionFactory;

    JMSContext context;

    void onStart(@Observes StartupEvent ev) {
        // Create a JMS context
        context = connectionFactory.createContext();
        var queue = context.createQueue("queue");
        var consumer = context.createConsumer(queue);

        // Handle the message asynchronously
        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage textMessage) {
                    System.out.println("Consumer A received message: " + textMessage.getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void onStop(@Observes ShutdownEvent ev) {
        context.close();
    }
}