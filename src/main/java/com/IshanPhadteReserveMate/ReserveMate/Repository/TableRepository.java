package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;

@Repository
public interface TableRepository extends MongoRepository<Table, String> {

    List<Table> findByRestaurantID(String restaurantID);

    List<Table> findByRestaurantIDAndIsOccupied(String restaurantID, boolean isOccupied);

    List<Table> findByRestaurantIDAndIsOccupiedFalse(String restaurantID);

    List<Table> findByRestaurantIDAndIsOccupiedTrue(String restaurantID);
}
