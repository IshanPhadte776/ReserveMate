package com.IshanPhadteReserveMate.ReserveMate.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.IshanPhadteReserveMate.ReserveMate.Service.MessageHandlerService;

@Controller
@RequestMapping("/messaging")
public class MessagingController {

    private MessageHandlerService messageHandlerService;


    public MessagingController(MessageHandlerService messageHandlerService) {
        this.messageHandlerService = messageHandlerService;
    }


    // @PostMapping("/sendUpdates")
    // public String sendMessage(@RequestParam String uniqueID, @RequestParam String message) {
    //     messageService.addMessage(uniqueID, message);
    //     return "Message sent to customer " + uniqueID;
    // }

    // Endpoint for the customer to retrieve their messages
    @GetMapping("/getUpdates")
    public List<String> getMessages(@RequestParam String uniqueID) {
        // Retrieve all messages for the given uniqueID
        return messageHandlerService.getMessagesForCustomer(uniqueID);
    }
    
}
