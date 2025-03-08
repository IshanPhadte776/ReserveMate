package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;
import com.IshanPhadteReserveMate.ReserveMate.Service.ReservationService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    ReservationService reservationService;


    public AdminDashboardController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Route for the Log Off action
    @RequestMapping("/dashboard")
    public RedirectView dashboard() {
        
        return new RedirectView("/admin-dashboard.html"); // Redirect to dashboard page
    }


    // Route for the Log Off action
    @RequestMapping("/logoff")
    public RedirectView logOff() {
        // Implement logoff logic, e.g., clearing the session or cookies
        return new RedirectView("/login.html"); // Redirect to login page
    }

    // Route for Check-in People
    @RequestMapping("/check-in")
    public RedirectView checkInPeople() {
        return new RedirectView("/reservation-form.html"); // Redirect to the check-in page
    }

    // Route for View Queue
    @RequestMapping("/queues")
    public RedirectView viewQueue() {
        return new RedirectView("/admin-customer-queues.html"); // Redirect to the queue page
    }

    // Route for Manage Tables
    @RequestMapping("/tables")
    public RedirectView manageTables() {
        return new RedirectView("/admin-tables.html"); // Redirect to the tables management page
    }

    
}
