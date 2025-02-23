package com.IshanPhadteReserveMate.ReserveMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReserveMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReserveMateApplication.class, args);
    }

    // @Service
    // static class MessageProducer implements CommandLineRunner {

    //     private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    //     @Autowired
    //     private JmsTemplate jmsTemplate;

    //     private String generateRandomTopic() {
    //         return "topic/dynamic/" + UUID.randomUUID().toString();
    //     }

	// 	@Override
    //     public void run(String... args) {
    //         String topic1 = generateRandomTopic();
    //         String topic2 = generateRandomTopic();

    //         String msg1 = "Hello from " + topic1;
    //         String msg2 = "Hello from " + topic2;

    //         logger.info("============= Sending to {}: {}", topic1, msg1);
    //         //jmsTemplate.convertAndSend(topic1, msg1);

    //         logger.info("============= Sending to {}: {}", topic2, msg2);
    //         //jmsTemplate.convertAndSend(topic2, msg2);
    //     }
    // }

    // @Component
    // static class MessageHandler {

    //     private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    //     @JmsListener(destination = "topic/dynamic/#", containerFactory = "cFactory")
    //     public void processMsg(Message<?> msg) {
    //         logMessage(msg);
    //     }

    //     private void logMessage(Message<?> msg) {
    //         StringBuilder msgAsStr = new StringBuilder("============= Received \nHeaders:");
    //         MessageHeaders hdrs = msg.getHeaders();
    //         msgAsStr.append("\nUUID: " + hdrs.getId());
    //         msgAsStr.append("\nTimestamp: " + hdrs.getTimestamp());
    //         for (String key : hdrs.keySet()) {
    //             msgAsStr.append("\n" + key + ": " + hdrs.get(key));
    //         }
    //         msgAsStr.append("\nPayload: " + msg.getPayload());
    //         logger.info(msgAsStr.toString());
    //     }
    // }
}
