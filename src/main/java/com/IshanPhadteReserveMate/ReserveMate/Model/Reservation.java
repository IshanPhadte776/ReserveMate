package com.IshanPhadteReserveMate.ReserveMate.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "Reservation")
@Data  // Lombok annotation for generating getters, setters, toString, etc.
public class Reservation {
    @Id
    private String id;  // MongoDB will use this as _id
    @Field("reservationID") // Maps the MongoDB _id field to reservationID
    private String reservationID;
    private String customerName;
    private String customerPhoneNumber;
    private String customerEmail;
    private String reservationTime;
    private String qrCode;
    private String viewURL;
    private String partySize;
    private String status;  // Possible values: "inqueue", "called", "seated", "left"
}
