package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;
import com.IshanPhadteReserveMate.ReserveMate.Repository.TableRepository;

public class TableService {
    @Autowired
    private TableRepository tableRepository;

    public List<Table> getAvailableTablesByRestaurantID(String restaurantID) {
        return tableRepository.findByRestaurantIDAndIsOccupied(restaurantID, false);
    }

}
