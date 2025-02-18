package ch.heigvd.amt.request;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;

@ApplicationScoped
public class RequestConsumer {

    @Inject
    ConnectionFactory connectionFactory;

    JMSContext context;

    void onStart(@Observes StartupEvent ev) {
        // Create a JMS context
        context = connectionFactory.createContext();
        var requestQueue = context.createQueue("request");
        var messageConsumer = context.createConsumer(requestQueue);

        // Handle the request asynchronously
        messageConsumer.setMessageListener(message -> {
            try {
                System.out.println("Received request");
                if (message instanceof TextMessage textMessage) {
                    // Create the response
                    var responseText = "Hello " + textMessage.getText() + "!";
                    var responseMessage = context.createTextMessage(responseText);

                    // Send the response to the destination specified in the request
                    var responseDestination = message.getJMSReplyTo();
                    try {
                        var responseProducer = context.createProducer();
                        responseProducer.send(responseDestination, responseMessage);
                        System.out.println("Sent response");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
