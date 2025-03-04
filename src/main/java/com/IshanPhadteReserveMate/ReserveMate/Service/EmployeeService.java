package com.IshanPhadteReserveMate.ReserveMate.Service;

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
}
