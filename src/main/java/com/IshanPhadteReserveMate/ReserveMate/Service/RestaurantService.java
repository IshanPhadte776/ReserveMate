package com.IshanPhadteReserveMate.ReserveMate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IshanPhadteReserveMate.ReserveMate.Model.Restaurant;
import com.IshanPhadteReserveMate.ReserveMate.Repository.RestaurantRepository;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantByID(String restaurantID) {
        return restaurantRepository.findByRestaurantID(restaurantID).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}
