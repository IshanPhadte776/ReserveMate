package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.IshanPhadteReserveMate.ReserveMate.Model.Employee;
import com.IshanPhadteReserveMate.ReserveMate.Model.Restaurant;
import com.IshanPhadteReserveMate.ReserveMate.Service.EmployeeService;
import com.IshanPhadteReserveMate.ReserveMate.Service.RestaurantService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeService employeeService;
    private final RestaurantService restaurantService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    public AuthController (EmployeeService employeeService, RestaurantService restaurantService){
        this.employeeService = employeeService;
        this.restaurantService = restaurantService;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(HttpServletRequest request, @RequestBody Map<String, String> loginRequest) {
        String restaurantID = loginRequest.get("restaurantID");
        String employeeID = loginRequest.get("employeeID");
        String password = loginRequest.get("password");

        logger.info("Login attempt for RestaurantID: " + restaurantID + ", EmployeeID: " + employeeID);

        Optional<Employee> employee = employeeService.getEmployeeByRestaurantIDAndEmployeeID(restaurantID, employeeID);

        Restaurant restaurant = restaurantService.getRestaurantByID(restaurantID);

        if (employee.isPresent() && employee.get().getEmployeePassword().equals(password)) {
            // Store user info in session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", employee.get());
            session.setAttribute("loggedInRestaurant", restaurant);

            logger.info("Login successful. Session created with ID: " + session.getId());

            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, String>> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Do not create a new session if one doesn't exist

        if (session != null && session.getAttribute("loggedInUser") != null) {
            Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
            Restaurant loggedInRestaurant = (Restaurant) session.getAttribute("loggedInRestaurant");

            return ResponseEntity.ok(Map.of(
                "message", "User is logged in",
                "restaurantID", loggedInUser.getRestaurantID(),
                "employeeID", loggedInUser.getEmployeeID(),
                "employeeName", loggedInUser.getEmployeeName(),
                "restaurantName", loggedInRestaurant.getRestaurantName(),
                "tableCount", String.valueOf(loggedInRestaurant.getTableCount())
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not logged in"));
        }
    }

}
