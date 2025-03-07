package com.IshanPhadteReserveMate.ReserveMate.Model;


// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// @Document(collection = "reservations")
// public class Reservation {
    
//     @Id
//     private String uniqueID;
//     private String name;
//     private String phoneNumber;
//     private String checkinTime;
//     private String qrCode;
//     private String viewUrl;

//     // Getters and setters

//     public String getUniqueID() {
//         return uniqueID;
//     }

//     public void setUniqueID(String uniqueID) {
//         this.uniqueID = uniqueID;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getPhoneNumber() {
//         return phoneNumber;
//     }

//     public void setPhoneNumber(String phoneNumber) {
//         this.phoneNumber = phoneNumber;
//     }

//     public String getCheckinTime() {
//         return checkinTime;
//     }

//     public void setCheckinTime(String checkinTime) {
//         this.checkinTime = checkinTime;
//     }

//     public String getQrCode() {
//         return qrCode;
//     }

//     public void setQrCode(String qrCode) {
//         this.qrCode = qrCode;
//     }

//     public String getViewUrl() {
//         return viewUrl;
//     }

//     public void setViewUrl(String viewUrl) {
//         this.viewUrl = viewUrl;
//     }
// }

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Reservation")
@Data  // Lombok annotation for generating getters, setters, toString, etc.
public class Reservation {
    @Id
    private String reservationID;
    
    private String customerName;
    private String customerPhoneNumber;
    private String reservationTime;
    private String qrCode;
    private String viewURL;
    private String status;  // Possible values: "inqueue", "called", "seated", "left"
}
