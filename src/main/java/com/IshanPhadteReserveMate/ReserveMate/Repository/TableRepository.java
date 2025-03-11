package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;

@Repository
public interface TableRepository extends MongoRepository<Table, String> {

    List<Table> findTableByRestaurantID(String restaurantID);

    List<Table> findTableByRestaurantIDAndIsOccupied(String restaurantID, boolean isOccupied);

    List<Table> findTableByRestaurantIDAndIsOccupiedFalse(String restaurantID);

    List<Table> findTableByRestaurantIDAndIsOccupiedTrue(String restaurantID);
}
