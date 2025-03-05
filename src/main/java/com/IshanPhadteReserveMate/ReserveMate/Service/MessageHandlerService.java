package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class MessageHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerService.class);

    // Map to hold the SseEmitters for each uniqueID (client)
    private final Map<String, SseEmitter> clientEmitters = new ConcurrentHashMap<>();

    // Register a new client for receiving messages (via SseEmitter)
    public SseEmitter registerClient(String uniqueID) {
        SseEmitter emitter = new SseEmitter(0L); // No timeout (client decides when to disconnect)
        logger.info("Received message for {}: {}", uniqueID);  // Log message

        emitter.onTimeout(() -> {
            logger.info("Client " + uniqueID + " timed out.");
            emitter.complete();
        });
    
        emitter.onCompletion(() -> {
            logger.info("Client " + uniqueID + " disconnected.");
        });
    
        // Store emitter for sending messages later
        clientEmitters.put(uniqueID, emitter);
    
        return emitter;
    }
    

    // Send a message to a specific client (via their uniqueID)
    // public void sendMessageToClient(String uniqueID, String message) {
    //     SseEmitter emitter = clientEmitters.get(uniqueID);
    //     if (emitter != null) {
    //         try {
    //             emitter.send(message);  // Send message to the client
    //             logger.info("Message sent to client {}: {}", uniqueID, message);
    //         } catch (Exception e) {
    //             logger.error("Failed to send message to client {}: {}", uniqueID, e.getMessage());
    //         }
    //     } else {
    //         logger.warn("No active client found for uniqueID: {}", uniqueID);
    //     }
    // }
}
