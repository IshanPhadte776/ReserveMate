package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.IshanPhadteReserveMate.ReserveMate.Model.Employee;
import com.IshanPhadteReserveMate.ReserveMate.Service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee testEmployee;

    @BeforeEach
    public void setUp() {
        testEmployee = new Employee();
        testEmployee.setRestaurantID("R001");
        testEmployee.setEmployeeID("E001");
        testEmployee.setEmployeePassword("password123");
    }

    @Test
    public void testLoginAdmin_Success() throws Exception {
        // Mock the employee service to return the test employee
        when(employeeService.getEmployeeByRestaurantIDAndEmployeeID("R001", "E001"))
                .thenReturn(Optional.of(testEmployee));

        // Create JSON payload for login
        String loginRequest = "{ \"restaurantID\": \"R001\", \"employeeID\": \"E001\", \"password\": \"password123\" }";

        // Perform POST request to login
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType("application/json")
                        .content(loginRequest))
                .andReturn();

        // Verify the response
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("{\"message\":\"Login successful\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void testLoginAdmin_Failure_InvalidCredentials() throws Exception {
        // Mock the employee service to return an empty result (invalid credentials)
        when(employeeService.getEmployeeByRestaurantIDAndEmployeeID("R001", "E001"))
                .thenReturn(Optional.empty());

        // Create JSON payload for login
        String loginRequest = "{ \"restaurantID\": \"R001\", \"employeeID\": \"E001\", \"password\": \"wrongpassword\" }";

        // Perform POST request to login
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType("application/json")
                        .content(loginRequest))
                .andReturn();

        // Verify the response
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
        assertEquals("{\"message\":\"Invalid credentials\"}", result.getResponse().getContentAsString());
    }
}
