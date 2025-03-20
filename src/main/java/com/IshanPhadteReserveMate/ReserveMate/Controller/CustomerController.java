package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.IshanPhadteReserveMate.ReserveMate.Service.ReservationService;



@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ReservationService reservationService;

    
    public CustomerController() {

    }

    @RequestMapping("/")
    public RedirectView view() {
        return new RedirectView("/customer.html"); // Redirect to customer view page
    }

    @RequestMapping("/{reservationId}")
    public ModelAndView viewPage(@PathVariable String reservationId) {
        Optional<Reservation> reservationOpt = reservationService.getReservationByID(reservationId);

        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            ModelAndView modelAndView = new ModelAndView("customer"); // Name of the HTML file (customer.html)
            modelAndView.addObject("reservation", reservation); // Pass the reservation object to the view
            return modelAndView;
        } else {
            // Handle reservation not found case
            ModelAndView modelAndView = new ModelAndView("error"); // You can create an error page if needed
            modelAndView.addObject("error", "Reservation not found");
            return modelAndView;
        }
    }
    
    @GetMapping("getReservationInfo/{reservationID}")
    public ResponseEntity<?> getReservationByID(@PathVariable String reservationID) {
        Optional<Reservation> reservationOpt = reservationService.getReservationByID(reservationID);

        return reservationOpt.map(reservation -> ResponseEntity.ok(
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
            ))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(Map.of("error", "Reservation not found")));
    }
    
    

}
