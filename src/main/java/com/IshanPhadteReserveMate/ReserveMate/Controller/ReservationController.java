package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Map<String, String> reservations = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @PostMapping("/create")
    public String createReservation(@RequestBody Reservation request) {
        String topicName = "dynamicTopic/" + UUID.randomUUID().toString();
        String reservationDetails = "Name: " + request.getName() + 
                                ", Phone: " + request.getPhoneNumber() + 
                                ", Check-in Time: " + request.getCheckinTime();

        // Save the reservation details in the map
        reservations.put(topicName, reservationDetails);

        // Send message to the dynamically generated topic
        jmsTemplate.convertAndSend(topicName, reservationDetails);

        return "http://localhost:8081/reservation/view/" + topicName;
    }

    @GetMapping("/view/dynamicTopic/{topic}")
    public String viewReservation(@PathVariable String topic) {
        // Retrieve the reservation details from the map
        String reservationDetails = reservations.get(topic);

        logger.info("Called");

        if (reservationDetails == null) {
            return "Reservation not found for topic: " + topic;
        }

        return "<html><head><title>Reservation</title></head>" +
            "<body><h1>Reservation Details:</h1><p>" + reservationDetails + "</p></body></html>";
    }

}
