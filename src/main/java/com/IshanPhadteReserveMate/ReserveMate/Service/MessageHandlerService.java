package com.IshanPhadteReserveMate.ReserveMate.Service;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@EnableAsync
public class MessageHandlerService {

    // Map to keep track of client emitters by unique reservation ID (or phone number)
    private final Map<String, SseEmitter> clientEmitters = new ConcurrentHashMap<>();
    // Map to track subscriptions by unique reservation ID
    private final Map<String, Boolean> subscriptions = new ConcurrentHashMap<>();
    
    @Autowired
    private JmsTemplate jmsTemplate;

    // Listener for incoming messages from JMS topics
    @JmsListener(destination = "reservation/#", containerFactory = "cFactory")
    public void processMsg(Message<String> msg) {
        // Extract unique reservation ID from the JMS destination (topic)
        String destination = msg.getHeaders().get("jms_destination").toString();
        String uniqueID = destination.substring(destination.lastIndexOf("/") + 1);

        // Check if the client is already subscribed
        if (!subscriptions.containsKey(uniqueID) || !subscriptions.get(uniqueID)) {
            // Only subscribe if not already subscribed
            subscribeToTopic(uniqueID);
            subscriptions.put(uniqueID, true); // Mark the unique ID as subscribed
        }

        // Process the message and send it to the corresponding SSE client
        sendMessageToClient(uniqueID, msg.getPayload());
    }

    // Subscribe a client to the topic
    private void subscribeToTopic(String uniqueID) {
        // Logic to subscribe to the JMS topic for the unique reservation ID
        // Assuming your JMS client setup handles subscription management
        // For example: Subscribe the uniqueID to the topic here
        
        // Logging the subscription
        System.out.println("Subscribing client " + uniqueID + " to topic");
        
        // Example: this could include connecting to the Solace broker with uniqueID as part of the topic subscription
    }

    // Send a message to the SSE client
    @Async
    public void sendMessageToClient(String uniqueID, String message) {
        // Retrieve the corresponding SSE emitter
        SseEmitter emitter = clientEmitters.get(uniqueID);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message, MediaType.TEXT_PLAIN));
            } catch (IOException e) {
                // If there's an issue with the client connection, remove the emitter
                clientEmitters.remove(uniqueID);
                System.err.println("Error sending message to client " + uniqueID + ": " + e.getMessage());
            }
        } else {
            System.out.println("No active SSE client found for uniqueID: " + uniqueID);
        }
    }

    // Endpoint to subscribe the client to live updates (via SSE)
    public SseEmitter subscribeToLiveUpdates(String uniqueID) {
        SseEmitter emitter = new SseEmitter();
        clientEmitters.put(uniqueID, emitter);

        emitter.onCompletion(() -> {
            // Clean up when client disconnects
            clientEmitters.remove(uniqueID);
            subscriptions.put(uniqueID, false); // Mark client as unsubscribed
        });

        emitter.onTimeout(() -> {
            // Handle timeout scenario
            clientEmitters.remove(uniqueID);
            subscriptions.put(uniqueID, false); // Mark client as unsubscribed
        });

        // Send an initial event to confirm subscription
        try {
            emitter.send(SseEmitter.event().name("subscription").data("Subscribed to live updates for reservation ID: " + uniqueID, MediaType.TEXT_PLAIN));
        } catch (IOException e) {
            // Handle errors during initial response
            clientEmitters.remove(uniqueID);
            subscriptions.put(uniqueID, false);
            System.err.println("Error during SSE subscription: " + e.getMessage());
        }

        return emitter;
    }

    // Optional: Method to manually disconnect a client from live updates
    public void unsubscribeFromLiveUpdates(String uniqueID) {
        SseEmitter emitter = clientEmitters.get(uniqueID);
        if (emitter != null) {
            emitter.complete(); // Close the connection
            clientEmitters.remove(uniqueID);
            subscriptions.put(uniqueID, false); // Mark the client as unsubscribed
        }
    }

    // Optional: Manual message dispatch for a specific client
    public void dispatchMessageToClient(String uniqueID, String message) {
        sendMessageToClient(uniqueID, message);
    }

    // For testing purposes: A method to manually send a message to all subscribed clients
    public void broadcastMessageToAllClients(String message) {
        for (Map.Entry<String, SseEmitter> entry : clientEmitters.entrySet()) {
            try {
                entry.getValue().send(SseEmitter.event().name("broadcast").data(message, MediaType.TEXT_PLAIN));
            } catch (IOException e) {
                System.err.println("Error broadcasting message to client " + entry.getKey() + ": " + e.getMessage());
            }
        }
    }
}
