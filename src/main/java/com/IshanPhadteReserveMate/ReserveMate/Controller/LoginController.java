package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.IshanPhadteReserveMate.ReserveMate.Model.Employee;
import com.IshanPhadteReserveMate.ReserveMate.Service.EmployeeService;

@Controller
@RequestMapping("/login")
public class LoginController {

    EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    public LoginController (EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    


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

    @PostMapping("/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> request) {
        String restaurantID = request.get("restaurantID");
        String employeeID = request.get("employeeID");
        String password = request.get("password");

        logger.info("Called");
        logger.info(restaurantID);
        logger.info(employeeID);
        logger.info(password);


        Optional<Employee> employee = employeeService.getEmployeeByRestaurantIDAndEmployeeID(restaurantID, employeeID);

        logger.info(employeeService.getAllEmployees().toString());

        logger.info(employee.toString());

        if (employee.isPresent() && employee.get().getEmployeePassword().equals(password)) {
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }
}
