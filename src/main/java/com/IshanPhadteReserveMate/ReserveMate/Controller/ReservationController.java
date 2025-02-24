package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.IshanPhadteReserveMate.ReserveMate.Service.ReservationService;
import com.IshanPhadteReserveMate.ReserveMate.Utils.QRCodeGenerator;
import com.google.zxing.WriterException;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final JmsTemplate jmsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationService reservationService,JmsTemplate jmsTemplate) {
        this.reservationService = reservationService;
        this.jmsTemplate = jmsTemplate;
    }
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createReservation(@RequestBody Map<String, String> request) throws WriterException, IOException {
        logger.info("Original Request: {}", request);


        Map<String, String> savedReservation = reservationService.createReservation(request);

        String reservationUrl = "http://localhost:8081/reservation/view/" + savedReservation.get("uniqueID");
        String qrCodeBase64 = QRCodeGenerator.generateQRCode(reservationUrl);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("viewUrl", reservationUrl);
        responseBody.put("qrCode", qrCodeBase64);
        responseBody.put("name", savedReservation.get("name"));
        responseBody.put("phoneNumber", savedReservation.get("phoneNumber"));
        responseBody.put("checkinTime", savedReservation.get("checkinTime"));
        responseBody.put("uniqueID", savedReservation.get("uniqueID"));

        logger.info("Reservation created with response data: {}", responseBody);



        return ResponseEntity.ok(responseBody);
    }
    
    @GetMapping("/queues")
    public Map<String, Map<String, String>> getAllReservations() {
        return reservationService.getAllReservations();
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

}
