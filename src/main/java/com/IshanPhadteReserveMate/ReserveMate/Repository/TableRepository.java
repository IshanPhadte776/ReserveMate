package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;

public interface TableRepository extends MongoRepository<Table, String> {
    List<Table> findByRestaurantID(String restaurantID);
    List<Table> findByRestaurantIDAndIsOccupied(String restaurantID, boolean isOccupied);
}
