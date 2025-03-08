package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IshanPhadteReserveMate.ReserveMate.Model.Employee;
import com.IshanPhadteReserveMate.ReserveMate.Repository.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeByID(String employeeID) {
        return employeeRepository.findByEmployeeID(employeeID).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeByRestaurantIDAndEmployeeID(String restaurantID, String employeeID) {
        return employeeRepository.findByRestaurantIDAndEmployeeID(restaurantID, employeeID);
    }
}
