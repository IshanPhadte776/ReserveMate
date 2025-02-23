package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@org.springframework.stereotype.Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final Map<String, String> reservations = new ConcurrentHashMap<>();
    private final JmsTemplate jmsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createReservation(@RequestBody Reservation request, Model model) throws WriterException, IOException {
        String uniqueId = UUID.randomUUID().toString();
        String topicName = "dynamicTopic/" + uniqueId;
        
        String reservationDetails = String.format("Name: %s, Phone Number: %s, Check-in Time: %s",
                request.getName(), request.getPhoneNumber(), request.getCheckinTime());

        // Save reservation
        reservations.put(uniqueId, reservationDetails);

        // Send message to the dynamically generated topic
        jmsTemplate.convertAndSend(topicName, reservationDetails);
        logger.info("Reservation created: {} -> {}", topicName, reservationDetails);

        String reservationUrl = "http://localhost:8081/reservation/view/" + uniqueId;
        String qrCodeBase64 = generateQRCode(reservationUrl);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("reservationDetails", reservationDetails);
        responseBody.put("qrCode", qrCodeBase64);
        responseBody.put("viewUrl", reservationUrl);

        //return ResponseEntity.ok(responseBody);

        // model.addAttribute("reservationDetails", reservationDetails);
        // model.addAttribute("qrCode", qrCodeBase64);
        // model.addAttribute("viewUrl", reservationUrl);

        // return showReservationCreatedPage(model);


        // Return response in JSON format
        return ResponseEntity.ok(responseBody);
        

    }


    @GetMapping("/reservation-created")
    public String showReservationCreatedPage(Model model) {

        logger.info("Called Correctly");

        model.asMap().forEach((key, value) -> logger.info("Model attribute: {} = {}", key, value));

        return "reservation-created";
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewReservation(@PathVariable String id) {
        logger.info("Retrieving reservation for ID: {}", id);

        String reservationDetails = reservations.get(id);
        if (reservationDetails == null) {
            logger.warn("Reservation not found for ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Reservation not found."));
        }

        return ResponseEntity.ok(Map.of("reservation", reservationDetails));
    }

    private String generateQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrBytes = pngOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(qrBytes); // Return as Base64 for embedding in HTML
    }

}
