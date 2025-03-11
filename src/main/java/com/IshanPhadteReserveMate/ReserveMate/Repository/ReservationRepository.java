package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    Optional<Reservation> findReservationByCustomerPhoneNumber(String phoneNumber);
    Optional<Reservation> findReservationByReservationID(String reservationID);
    List<Reservation> findReservationByStatusNot(String status);  // "left" means completed, filter out those
    List<Reservation> findReservationByStatus(String status); 

}
