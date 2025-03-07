package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.IshanPhadteReserveMate.ReserveMate.Repository.ReservationRepository;
import com.IshanPhadteReserveMate.ReserveMate.Service.ReservationService;
import com.IshanPhadteReserveMate.ReserveMate.Utils.QRCodeGenerator;
import com.google.zxing.WriterException;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }
    // @PostMapping("/create")
    // public ResponseEntity<Map<String, String>> createReservation(@RequestBody Map<String, String> request) throws WriterException, IOException {
    //     logger.info("Original Request: {}", request);

    //     Map<String, String> savedReservation = reservationService.createReservation(request);

    //     String reservationUrl = "http://localhost:8081/reservation/view/" + savedReservation.get("uniqueID");
    //     String qrCodeBase64 = QRCodeGenerator.generateQRCode(reservationUrl);

    //     Map<String, String> responseBody = new HashMap<>();
    //     responseBody.put("viewUrl", reservationUrl);
    //     responseBody.put("qrCode", qrCodeBase64);
    //     responseBody.put("name", savedReservation.get("name"));
    //     responseBody.put("phoneNumber", savedReservation.get("phoneNumber"));
    //     responseBody.put("checkinTime", savedReservation.get("checkinTime"));
    //     responseBody.put("uniqueID", savedReservation.get("uniqueID"));

    //     logger.info("Reservation created with response data: {}", responseBody);



    //     return ResponseEntity.ok(responseBody);
    // }
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createReservation(@RequestBody Map<String, String> request) throws WriterException, IOException {

        logger.info("Creating Reservation");

        logger.info(request.toString());



        // Create a new reservation object
        Reservation reservation = new Reservation();
        String reservationID = generateUniqueID();
        reservation.setReservationID(reservationID); // Set reservationID
        reservation.setCustomerName(request.get("name"));
        reservation.setCustomerPhoneNumber(request.get("phoneNumber"));
        reservation.setReservationTime(request.get("checkinTime")); // Assuming reservationTime is in ISO format
        reservation.setStatus("inqueue");  // Set initial status to "in-queue"
        reservation.setPartySize(request.get("partySize"));

        

        // Generate QR Code and View URL
        String reservationUrl = "http://localhost:8081/view/" + reservationID;
        reservation.setViewURL(reservationUrl);
        reservation.setQrCode(QRCodeGenerator.generateQRCode(reservationUrl));  // Assuming QRCodeGenerator is implemented

        logger.info(reservation.toString());

        // Save reservation to MongoDB
        reservationRepository.save(reservation);

        // Return the saved reservation as a Map
        return ResponseEntity.ok(
            Map.of(
                "uniqueID", reservation.getReservationID(),
                "customerName", reservation.getCustomerName(),
                "customerPhoneNumber", reservation.getCustomerPhoneNumber(),
                "reservationTime", reservation.getReservationTime(),
                "partySize", reservation.getPartySize(),
                "qrCode", reservation.getQrCode(),
                "viewURL", reservation.getViewURL(),
                "status", reservation.getStatus()
            )
        );
    }

     // Get all active reservations
    @GetMapping("/getReservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getAllActiveReservations();
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No reservations found
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);  // Return active reservations
    }

    @PutMapping("/updateStatus/{reservationID}")
    public ResponseEntity<String> updateStatus(@PathVariable String reservationID, @RequestParam String status) {
        if (!List.of("inqueue", "called", "seated", "left").contains(status)) {
            return ResponseEntity.badRequest().body("Invalid status.");
        }
        
        boolean updated = reservationService.updateReservationStatus(reservationID, status);
        
        if (updated) {
            return ResponseEntity.ok("Reservation status updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Reservation not found.");
        }
    }


    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<Map<String, String>> deleteReservation(@PathVariable String id) {
        logger.info("Delete Reservation");
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        

        if (reservation.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Reservation not found"));
        }

        reservationService.deleteReservationById(id);

        return ResponseEntity.ok(Map.of("message", "Reservation deleted successfully"));
    }
    

    // @GetMapping("/view/{id}")
    // public ResponseEntity<?> viewReservation(@PathVariable String id) {

    //     String reservationDetails = reservations.get(id);
    //     if (reservationDetails == null) {
    //         logger.warn("Reservation not found for ID: {}", id);
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(Map.of("error", "Reservation not found."));
    //     }

    //     return ResponseEntity.ok(Map.of("reservation", reservationDetails));
    // }

    private String generateUniqueID() {
        // You can implement a custom unique ID generation logic or use a UUID
        return java.util.UUID.randomUUID().toString();
    }



}
