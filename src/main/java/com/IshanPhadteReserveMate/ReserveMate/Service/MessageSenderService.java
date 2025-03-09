package com.IshanPhadteReserveMate.ReserveMate.Service;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private final JmsTemplate jmsTemplate;

    public MessageSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // Method to send a message to a specific customer's topic
    public void sendMessage(String customerID, String message) {
        //String destination = "Hello/updates/" + customerID;  // Customer-specific topic
        String destination = "Hello/updates";  // Customer-specific topic

        System.out.println("📩 Sending message to: " + destination + " | Message: " + message);

        jmsTemplate.convertAndSend(destination, message);
    }
}
