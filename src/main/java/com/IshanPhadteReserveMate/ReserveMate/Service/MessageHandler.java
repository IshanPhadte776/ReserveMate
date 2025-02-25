// package com.IshanPhadteReserveMate.ReserveMate.Service;


// import java.io.IOException;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.MediaType;
// import org.springframework.jms.annotation.JmsListener;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageHeaders;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

// @Component
// public class MessageHandler {
    
//     private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
//     private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

//     // Listen for messages on the customer's topic
//     @JmsListener(destination = "updates/#", containerFactory = "cFactory")
//     public void processMsg(Message<String> msg) {
//         logMessage(msg);

//         // Extract uniqueID from the destination
//         String destination = msg.getHeaders().get("jms_destination").toString();
//         String uniqueID = destination.substring(destination.lastIndexOf("/") + 1);

//         // Send the message to the appropriate SSE connection
//         SseEmitter emitter = emitters.get(uniqueID);
//         if (emitter != null) {
//             try {
//                 emitter.send(SseEmitter.event()
//                         .name("message")
//                         .data(msg.getPayload(), MediaType.TEXT_PLAIN));
//             } catch (IOException e) {
//                 emitters.remove(uniqueID); // Remove emitter if there's an issue
//             }
//         }
//     }

//     // Store the SSE connection for the customer
//     public SseEmitter subscribe(String uniqueID) {
//         SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//         emitters.put(uniqueID, emitter);

//         emitter.onCompletion(() -> emitters.remove(uniqueID));
//         emitter.onTimeout(() -> emitters.remove(uniqueID));

//         return emitter;
//     }

//     private void logMessage(Message<?> msg) {
//         StringBuilder msgAsStr = new StringBuilder("============= Received \nHeaders:");
//         MessageHeaders hdrs = msg.getHeaders();
//         msgAsStr.append("\nUUID: ").append(hdrs.getId());
//         msgAsStr.append("\nTimestamp: ").append(hdrs.getTimestamp());
//         for (String key : hdrs.keySet()) {
//             msgAsStr.append("\n").append(key).append(": ").append(hdrs.get(key));
//         }
//         msgAsStr.append("\nPayload: ").append(msg.getPayload());
//         logger.info(msgAsStr.toString());
//     }
// }
