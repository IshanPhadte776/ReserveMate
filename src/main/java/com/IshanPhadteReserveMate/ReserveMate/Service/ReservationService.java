package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.IshanPhadteReserveMate.ReserveMate.Repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    private final Map<String, Map<String,String>> reservations = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    // public ReservationService(ReservationRepository reservationRepository) {
    //     this.reservationRepository = reservationRepository;
    // }

    // public Map<String,String> createReservation(Map<String,String> request) {
    //     String uniqueId = UUID.randomUUID().toString();
    //     request.put("uniqueID", uniqueId);

    //     String topicName = "reservation/" + uniqueId;
    //     jmsTemplate.convertAndSend(topicName, request); // Send full object instead of string

    //     logger.info("Reservation created: {} -> {}", topicName, request);

    //     reservations.put(uniqueId, request);

    //     return request; // Return full reservation object
    // }


    public  Optional<Reservation> getReservationByID(String reservationID) {
        return reservationRepository.findReservationByReservationID(reservationID);
    }

    public Optional<Reservation> getReservationByReservationID(String reservationID) {
        return reservationRepository.findReservationByReservationID(reservationID);
    }

    public Reservation getReservationByPhoneNumber(String phoneNumber) {
        return reservationRepository.findReservationByCustomerPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("Reservation not found with Phone Number: " + phoneNumber));
    }

    public List<Reservation> getAllActiveReservations() {
        return reservationRepository.findReservationByStatusNot("left");  // Filter out "left" reservations
    }

    public boolean updateReservationStatus(String reservationID, String newStatus) {


        Optional<Reservation> optionalReservation = reservationRepository.findReservationByReservationID(reservationID);

        System.out.println(optionalReservation);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus(newStatus);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public void deleteReservationById(String id) {
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByStatus(String status) {
        return reservationRepository.findReservationByStatus("seated");  // Filter out "left" reservations
    } 

}
