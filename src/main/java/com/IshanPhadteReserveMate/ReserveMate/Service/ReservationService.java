package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.IshanPhadteReserveMate.ReserveMate.Repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final Map<String, Map<String,String>> reservations = new ConcurrentHashMap<>();
    private final JmsTemplate jmsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(JmsTemplate jmsTemplate, ReservationRepository reservationRepository) {
        this.jmsTemplate = jmsTemplate;
        this.reservationRepository = reservationRepository;
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


    public Reservation getReservationByID(String reservationID) {
        return reservationRepository.findByReservationID(reservationID).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation getReservationByPhoneNumber(String phoneNumber) {
        return reservationRepository.findByCustomerPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("Reservation not found with Phone Number: " + phoneNumber));
    }

    public List<Reservation> getAllActiveReservations() {
        return reservationRepository.findByStatusNot("left");  // Filter out "left" reservations
    }

    public boolean updateReservationStatus(String reservationID, String newStatus) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationID);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus(newStatus);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

}
