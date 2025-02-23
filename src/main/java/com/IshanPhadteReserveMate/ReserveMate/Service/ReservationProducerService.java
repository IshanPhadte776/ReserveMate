// package com.IshanPhadteReserveMate.ReserveMate.Service;

// import java.util.Map;

// import org.springframework.cloud.stream.function.StreamBridge;
// import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;






// @Service
// @RestController
// @RequestMapping("/reservation")
// public class ReservationProducerService {

//     private final StreamBridge streamBridge;

//     public ReservationProducerService(StreamBridge streamBridge) {
//         this.streamBridge = streamBridge;
//     }
    
//     @PostMapping("/create")
//     public String createReservation(@RequestBody Map<String, String> requestData) {
//         String name = requestData.get("name");
//         String phone = requestData.get("phone");
//         String checkinTime = requestData.get("checkinTime");

//         String reservationMessage = String.format("Name: %s, Phone: %s, Time: %s", name, phone, checkinTime);

//         // Send message to the Solace broker
//         streamBridge.send("reservations/new", reservationMessage);
        
//         return "Reservation request sent!";
//     }
// }
