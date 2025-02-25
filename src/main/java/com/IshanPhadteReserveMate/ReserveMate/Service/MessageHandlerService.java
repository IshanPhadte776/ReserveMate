package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerService.class);

    // Map to store messages per customer (uniqueID -> List of messages)
    private final Map<String, List<String>> customerMessages = new ConcurrentHashMap<>();

    @JmsListener(destination = "updates/#", containerFactory = "cFactory")
    public void handleMessage(Message<String> msg) {
        logMessageDetails(msg);

        // Extract the uniqueID from the destination
        String destination = msg.getHeaders().get("jms_destination").toString();
        String uniqueID = destination.substring(destination.lastIndexOf("/") + 1);

        // Get the list of messages for this customer (uniqueID)
        List<String> messagesForCustomer = customerMessages.computeIfAbsent(uniqueID, k -> new ArrayList<>());
        messagesForCustomer.add(msg.getPayload()); // Add the new message

        logger.info("Message added for uniqueID: {} -> {}", uniqueID, msg.getPayload());
    }

    // Method to retrieve messages for a specific customer by uniqueID
    public List<String> getMessagesForCustomer(String uniqueID) {
        return customerMessages.getOrDefault(uniqueID, new ArrayList<>());
    }

    private void logMessageDetails(Message<?> msg) {
        StringBuilder msgDetails = new StringBuilder("============= Received \nHeaders:");
        MessageHeaders headers = msg.getHeaders();
        msgDetails.append("\nUUID: ").append(headers.getId());
        msgDetails.append("\nTimestamp: ").append(headers.getTimestamp());
        for (String key : headers.keySet()) {
            msgDetails.append("\n").append(key).append(": ").append(headers.get(key));
        }
        msgDetails.append("\nPayload: ").append(msg.getPayload());
        logger.info(msgDetails.toString());
    }
}
