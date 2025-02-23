package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ResponseEntity<String> createReservation(@RequestBody Reservation request, RedirectAttributes redirectAttributes) throws WriterException, IOException {
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

        // Return ModelAndView with variables
        // ModelAndView modelAndView = new ModelAndView("redirect:/reservation-created");
        // modelAndView.addObject("reservationDetails", reservationDetails);
        // modelAndView.addObject("qrCode", qrCodeBase64);
        // modelAndView.addObject("viewUrl", "http://localhost:8081/reservation/view/" + uniqueId);
        
        // return modelAndView;

        redirectAttributes.addFlashAttribute("reservationDetails", reservationDetails);
        redirectAttributes.addFlashAttribute("qrCode", qrCodeBase64);
        redirectAttributes.addFlashAttribute("viewUrl", reservationUrl);
        
        return ResponseEntity.ok(String.format("Reservation created successfully. View your reservation: <a href='%s'>%s</a><br> QR Code: <img src='data:image/png;base64,%s'/>", 
                                          reservationUrl, reservationUrl, qrCodeBase64));


        // // Return response with URL
        // Map<String, String> response = new HashMap<>();
        // response.put("message", "Reservation created successfully.");
        // response.put("view_url", "http://localhost:8081/reservation/view/" + uniqueId);
        // response.put("qr_code", qrCodeBase64);  // Include QR code in the response
        
        // return ResponseEntity.ok(response);
    }

    // @GetMapping("/reservation-created")
    // public String showReservationCreatedPage(Model model) {
    //     // Data already set in the previous request can be accessed here
    //     return "reservation-created";  // Display the reservation-created page
    // }

    @GetMapping("/reservation-created")
    public String showReservationCreatedPage(Model model) {
        // Data will be passed from the POST request and displayed on the reservation-created page
        return "reservation-created";  // This should refer to reservation-created.html in your templates folder
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
