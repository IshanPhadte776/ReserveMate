package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    Optional<Reservation> findByCustomerPhoneNumber(String phoneNumber);
    Optional<Reservation> findByReservationID(String reservationID);
    List<Reservation> findByStatusNot(String status);  // "left" means completed, filter out those
    List<Reservation> findByStatus(String status); 

}
