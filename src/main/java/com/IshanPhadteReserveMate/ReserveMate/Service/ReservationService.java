package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final Map<String, Map<String,String>> reservations = new ConcurrentHashMap<>();
    private final JmsTemplate jmsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(JmsTemplate jmsTemplate) {
        
        this.jmsTemplate = jmsTemplate;
    }

    public Map<String,String> createReservation(Map<String,String> request) {
        String uniqueId = UUID.randomUUID().toString();
        request.put("uniqueID", uniqueId);

        String topicName = "updates/" + uniqueId;
        jmsTemplate.convertAndSend(topicName, request); // Send full object instead of string

        logger.info("Reservation created: {} -> {}", topicName, request);

        reservations.put(uniqueId, request);

        return request; // Return full reservation object
    }

    // Get all active reservations
    public Map<String, Map<String,String>> getAllReservations() {
        return reservations;
    }
}
