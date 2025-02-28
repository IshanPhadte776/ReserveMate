package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.zxing.WriterException;

// @RestController
// @RequestMapping("/customer")
// public class CustomerController {
//     //private final MessageHandler messageHandler;
//     private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


//     public CustomerController() {
//         //this.messageHandler = messageHandler;
//     }

//     @PostMapping("/create")
//     public ResponseEntity<Map<String, String>> createReservation(@RequestBody Map<String, String> request) throws WriterException, IOException {
// }
