package com.IshanPhadteReserveMate.ReserveMate.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Restaurant")
@Data  // Lombok annotation for generating getters, setters, toString, etc.
public class Restaurant {
    @Id
    private String restaurantID;
    private String restaurantName;
    private int tableCount;
}

