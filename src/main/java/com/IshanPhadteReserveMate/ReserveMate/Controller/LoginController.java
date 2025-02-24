package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/")
    public String showLoginPage() {
        return "redirect:/login.html";  // Render the login.html page when accessing localhost:8081
    }

    @PostMapping("/admin-login")
    public String handleLogin(@RequestParam String companyName, @RequestParam String password) {
        // Here, you can handle the login logic. For example, check if companyName and password match.
        if ("admin".equals(companyName) && "password123".equals(password)) {
            return "redirect:/admin-dashboard.html";  // Redirect to the admin dashboard page
        } else {
            return "redirect:/login.html";  // If login fails, show the login page again
        }
    }
}
