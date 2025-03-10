package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;
import com.IshanPhadteReserveMate.ReserveMate.Service.TableService;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // Get all tables
    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        List<Table> tables = tableService.getAllTables();
        return tables.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tables);
    }

    // Get all tables for a specific restaurant
    @GetMapping("getAllTables/{restaurantID}")
    public ResponseEntity<List<Table>> getTablesByRestaurantID(@PathVariable String restaurantID) {
        List<Table> tables = tableService.getTablesByRestaurantID(restaurantID);
        return tables.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tables);
    }

    // Get available (empty) tables for a specific restaurant
    @GetMapping("getEmptyTables/{restaurantID}")
    public ResponseEntity<List<Table>> getAvailableTables(@PathVariable String restaurantID) {
        List<Table> availableTables = tableService.getAvailableTablesByRestaurantID(restaurantID);
        return availableTables.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availableTables);
    }

    // Get occupied tables for a specific restaurant
    @GetMapping("/getOccupiedTables/{restaurantID}")
    public ResponseEntity<List<Table>> getOccupiedTables(@PathVariable String restaurantID) {
        List<Table> occupiedTables = tableService.getOccupiedTablesByRestaurantID(restaurantID);
        return occupiedTables.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(occupiedTables);
    }

    // Update table status (occupied or available)
    @PutMapping("/{tableID}/status")
    public ResponseEntity<Void> updateTableStatus(
            @PathVariable String tableID,
            @RequestParam boolean isOccupied) {

        boolean updated = tableService.updateTableStatus(tableID, isOccupied);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
