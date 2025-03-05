package com.IshanPhadteReserveMate.ReserveMate.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.IshanPhadteReserveMate.ReserveMate.Service.MessageHandlerService;
import com.IshanPhadteReserveMate.ReserveMate.Service.MessageSenderService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    
    private final MessageHandlerService messageHandlerService;
    private final MessageSenderService messageSenderService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    

    public MessageController(MessageHandlerService messageHandlerService, MessageSenderService messageSenderService) {
        this.messageHandlerService = messageHandlerService;
        this.messageSenderService = messageSenderService;
    }

    @GetMapping(value = "/updates/{uniqueID}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToMessages(@PathVariable String uniqueID) {
        logger.info("Called Subscribe");
        // Register the client for updates with the uniqueID
        return messageHandlerService.registerClient(uniqueID);
    }


    @PostMapping("/updates/{reservationID}")
    public void sendCalledUpUpdate(@PathVariable String reservationID) {
        messageSenderService.sendMessage(reservationID, "Your Table is Ready");
    }



}
