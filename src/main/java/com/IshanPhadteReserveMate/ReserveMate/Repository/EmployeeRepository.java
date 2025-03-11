package com.IshanPhadteReserveMate.ReserveMate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.IshanPhadteReserveMate.ReserveMate.Model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAll(); // This retrieves all employees from the collection
    Optional<Employee> findEmployeeByEmployeeID(String employeeID);
    Optional<Employee> findEmployeeByRestaurantIDAndEmployeeID(String restaurantID, String employeeID);

}
