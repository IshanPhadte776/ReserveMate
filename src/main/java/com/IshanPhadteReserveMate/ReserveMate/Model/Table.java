package com.IshanPhadteReserveMate.ReserveMate.Model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Table")
public class Table {
    
    private String tableID;
    private String restaurantID;
    private int  maxSize;
    private boolean isOccupied;

}
