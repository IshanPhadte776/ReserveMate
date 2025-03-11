package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IshanPhadteReserveMate.ReserveMate.Model.Table;
import com.IshanPhadteReserveMate.ReserveMate.Service.ReservationService;
import com.IshanPhadteReserveMate.ReserveMate.Service.TableService;

@RestController
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService tableService;
    @Autowired
    private ReservationService reservationService;


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
        System.out.println("Called emptyTables w/" + restaurantID);
        List<Table> availableTables = tableService.getAvailableTablesByRestaurantID(restaurantID);
        return availableTables.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availableTables);
    }

    // Get occupied tables for a specific restaurant
    @GetMapping("/getOccupiedTables/{restaurantID}")
    public ResponseEntity<List<Map<String, Object>>> getOccupiedTables(@PathVariable String restaurantID) {
        System.out.println("Called occupied Tables w/" + restaurantID);

        List<Table> occupiedTables = tableService.getOccupiedTablesByRestaurantID(restaurantID);

        System.out.println("OccupiedTable: " + occupiedTables);


        if (occupiedTables.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Fetch reservation details and merge data
        List<Map<String, Object>> result = occupiedTables.stream()
            .map(table -> {
                Map<String, Object> combinedData = new HashMap<>();
                
                // Convert Table entity to key-value pairs
                combinedData.put("tableID", table.getTableID());
                combinedData.put("restaurantID", table.getRestaurantID());
                combinedData.put("maxSize", table.getMaxSize());
                combinedData.put("isOccupied", table.isOccupied());

                System.out.println(table.getTableID());
                System.out.println(table.getRestaurantID());
                System.out.println(String.valueOf(table.getMaxSize()));
                System.out.println(String.valueOf(table.isOccupied()));

                System.out.println(reservationService.getReservationById("R001"));

                System.out.println(reservationService.getReservationById(table.getReservationID()));

                // Fetch Reservation details
                reservationService.getReservationById(table.getReservationID())
                    .ifPresent(reservation -> {
                        combinedData.put("reservationID", reservation.getReservationID());
                        combinedData.put("customerName", reservation.getCustomerName());
                        combinedData.put("customerPhoneNumber", reservation.getCustomerPhoneNumber());
                        combinedData.put("partySize", reservation.getPartySize());
                        combinedData.put("reservationTime", reservation.getReservationTime());
                        combinedData.put("status", reservation.getStatus());
                    });

                return combinedData;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
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
