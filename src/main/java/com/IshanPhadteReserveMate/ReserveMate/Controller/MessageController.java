package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IshanPhadteReserveMate.ReserveMate.Service.MessageSenderService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    
    private final MessageSenderService messageSenderService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    

    public MessageController( MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    //  @PutMapping("/createQueueTopic/{reservationID}")
    // public ResponseEntity<String> createQueueTopic(@PathVariable String reservationID) {
    //     messageSenderService.createQueueAndSubscribe(reservationID);
    //     return ResponseEntity.ok("Success");

    // }


}
