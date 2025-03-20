package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    public CustomerController() {
    }

    @RequestMapping("/")
    public RedirectView view() {
        return new RedirectView("/customer.html"); // Redirect to customer view page
    }
    

}
