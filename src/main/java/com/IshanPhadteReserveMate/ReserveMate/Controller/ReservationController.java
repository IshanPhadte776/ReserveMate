package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class ReservationController {

    // private final ReservationConsumerService reservationConsumerService;

    // public ReservationController(ReservationConsumerService reservationConsumerService) {
    //     this.reservationConsumerService = reservationConsumerService;
    // }

    // @GetMapping("/reservation/{phoneNumber}")
    // public String getReservation(@PathVariable String phoneNumber, Model model) {
    //     Reservation reservation = reservationConsumerService.getReservation(phoneNumber);
        
    //     if (reservation == null) {
    //         return "notfound"; // Redirect to a 404 page if no reservation exists
    //     }

    //     model.addAttribute("reservation", reservation);
    //     return "reservation"; // This returns the HTML page
    // }
}
