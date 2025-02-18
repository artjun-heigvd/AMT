package ch.heigvd.amt.request;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.TextMessage;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.concurrent.CompletableFuture;

@Path("/request")
public class RequestProducer {

    @Inject
    ConnectionFactory connectionFactory;

    @GET
    @Path("/sync")
    public String sync(@QueryParam("name") String name) {
        String response = "No response";

        // Create a JMS context
        try(var context = connectionFactory.createContext()) {

            // Create the queue and the producer
            var requestQueue = context.createQueue("request");
            var requestProducer = context.createProducer();

            // Create the request
            var requestMessage = context.createTextMessage();
            requestMessage.setText(name);

            // Create the response queue and set it as the reply-to destination
            var responseQueue = context.createTemporaryQueue();
            requestMessage.setJMSReplyTo(responseQueue);

            // Send the request
            requestProducer.send(requestQueue, requestMessage);
            System.out.println("Sent request");

            // Block and wait for the response
            var responseConsumer = context.createConsumer(responseQueue);
            var responseMessage = responseConsumer.receive(10000L);
            System.out.println("Received response");
            if (responseMessage instanceof TextMessage textMessage) {
                response = textMessage.getText();
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }

    @GET
    @Path("/async")
    public Uni<String> async(@QueryParam("name") String name) {
        try {

            // Create a JMS context
            var context = connectionFactory.createContext();
            var future = new CompletableFuture<String>();
            future.thenAccept(s -> context.close());

            // Create the queue and the producer
            var requestQueue = context.createQueue("request");
            var requestProducer = context.createProducer();

            // Create the request
            var requestMessage = context.createTextMessage();
            requestMessage.setText(name);

            // Create the response queue and set it as the reply-to destination
            var responseQueue = context.createTemporaryQueue();
            requestMessage.setJMSReplyTo(responseQueue);
            requestProducer.send(requestQueue, requestMessage);
            System.out.println("Sent request");

            // Set the response asynchronously with a message listener
            var responseConsumer = context.createConsumer(responseQueue);
            responseConsumer.setMessageListener(message -> {
                try {
                    System.out.println("Received response");
                    if (message instanceof TextMessage textMessage) {
                        future.complete(textMessage.getText());
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            return Uni.createFrom().future(future);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }
}
