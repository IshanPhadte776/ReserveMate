package com.IshanPhadteReserveMate.ReserveMate.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Employee")
@Data  // Lombok annotation for generating getters, setters, toString, etc.
public class Employee {
    
    private String employeeID;
    private String employeeName;
    private String employeePassword;
    private String restaurantID;  // Foreign key referencing the restaurant
}
