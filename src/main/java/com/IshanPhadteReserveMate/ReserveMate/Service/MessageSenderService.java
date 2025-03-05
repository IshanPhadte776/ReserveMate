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
        String destination = "updates/" + customerID;  // Customer-specific topic
        jmsTemplate.convertAndSend(destination, message);
    }
}
