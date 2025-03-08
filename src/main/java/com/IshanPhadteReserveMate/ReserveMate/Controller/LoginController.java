package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    @GetMapping("/")
    public RedirectView showLoginPage() {
        //return "redirect:/login.html";  // Render the login.html page when accessing localhost:8081
        return new RedirectView("/login.html");
    }

    // @PostMapping("/admin-login")
    // public String handleLogin(@RequestParam String companyName, @RequestParam String password) {
    //     // Here, you can handle the login logic. For example, check if companyName and password match.
    //     if ("admin".equals(companyName) && "password123".equals(password)) {
    //         return "redirect:/admin-dashboard.html";  // Redirect to the admin dashboard page
    //     } else {
    //         return "redirect:/login.html";  // If login fails, show the login page again
    //     }
    // }

    // @PostMapping("/login")
    // public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> request) {
    //     String restaurantID = request.get("restaurantID");
    //     String employeeID = request.get("employeeID");
    //     String password = request.get("password");

    //     Optional<Employee> employee = employeeRepository.findByRestaurantIDAndEmployeeID(restaurantID, employeeID);

    //     if (employee.isPresent() && employee.get().getPassword().equals(password)) {
    //         return ResponseEntity.ok(Map.of("message", "Login successful"));
    //     } else {
    //         return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
    //     }
    // }
}
