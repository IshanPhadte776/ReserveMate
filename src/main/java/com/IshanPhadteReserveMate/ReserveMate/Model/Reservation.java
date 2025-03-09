package com.IshanPhadteReserveMate.ReserveMate.Model;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Reservation")
@Data  // Lombok annotation for generating getters, setters, toString, etc.
public class Reservation {
    private String reservationID;
    private String customerName;
    private String customerPhoneNumber;
    private String reservationTime;
    private String qrCode;
    private String viewURL;
    private String partySize;
    private String status;  // Possible values: "inqueue", "called", "seated", "left"
}
