package com.IshanPhadteReserveMate.ReserveMate.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;
import com.IshanPhadteReserveMate.ReserveMate.Repository.TableRepository;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    // Get all tables
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    // Get all tables for a specific restaurant
    public List<Table> getTablesByRestaurantID(String restaurantID) {
        return tableRepository.findTableByRestaurantID(restaurantID);
    }

    // Get only available (empty) tables for a restaurant
    public List<Table> getAvailableTablesByRestaurantID(String restaurantID) {
        return tableRepository.findTableByRestaurantIDAndIsOccupiedFalse(restaurantID);
    }

    // Get only occupied tables for a restaurant
    public List<Table> getOccupiedTablesByRestaurantID(String restaurantID) {
        return tableRepository.findTableByRestaurantIDAndIsOccupiedTrue(restaurantID);
    }

    // Update table status (occupied or available)
    public boolean updateTableStatus(String tableID, boolean isOccupied) {
        Optional<Table> tableOptional = tableRepository.findById(tableID);

        if (tableOptional.isPresent()) {
            Table table = tableOptional.get();
            table.setOccupied(isOccupied);
            tableRepository.save(table);
            return true;
        }

        return false;
    }
}
