package ch.heigvd.amt.queue;

import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/queue")
public class QueueProducer {

    @Inject
    ConnectionFactory connectionFactory;

    @GET
    public String send(@QueryParam("text") String text) {
        // Create a JMS context
        try(var context = connectionFactory.createContext()) {

            // Create the queue and the producer
            var queue = context.createQueue("queue");
            var producer = context.createProducer();

            // Create the message
            var message = context.createTextMessage();
            message.setText(text);

            // Send the message
            producer.send(queue, message);
            System.out.println("Sent message: " + text);

            return "Message sent";
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }
}

