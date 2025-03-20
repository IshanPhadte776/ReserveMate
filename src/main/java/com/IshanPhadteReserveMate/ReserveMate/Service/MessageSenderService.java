package com.IshanPhadteReserveMate.ReserveMate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;


@Service
public class MessageSenderService {

    @Autowired
	private JmsTemplate jmsTemplate;

    @PostConstruct
	private void customizeJmsTemplate() {
		// Update the jmsTemplate's connection factory to cache the connection
		CachingConnectionFactory ccf = new CachingConnectionFactory();
		ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
		jmsTemplate.setConnectionFactory(ccf);

		// By default Spring Integration uses Queues, but if you set this to true you
		// will send to a PubSub+ topic destination
		jmsTemplate.setPubSubDomain(false);
	}


    // Method to send a message to a specific customer's topic
    public void sendMessage(String reservationID, String message) {
        //String destination = "Hello/updates/" + customerID;  // Customer-specific topic
        //String destination = "Hello/updates";  // Customer-specific topic
        String destinationQueue = "customerQueue/" + reservationID;


        System.out.println("📩 Sending message to: " + destinationQueue + " | Message: " + message);

        jmsTemplate.convertAndSend(destinationQueue, message);
    }

    // public void createQueueAndSubscribe(String reservationID) {
    //     try (Connection connection = connectionFactory.createConnection();
    //          Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

    //         String queueName = "customerQueue/" + reservationID;
    //         String topicName = "customerTopic/" + reservationID;

    //         // Create a Queue
    //         Queue queue = session.createQueue(queueName);

    //         // Create a Topic
    //         Topic topic = session.createTopic(topicName);

    //         // Subscribe Queue to Topic
    //         session.createConsumer(topic).setMessageListener(message -> {
    //             try {
    //                 System.out.println("Received Message: " + ((TextMessage) message).getText());
    //             } catch (JMSException e) {
    //                 e.printStackTrace();
    //             }
    //         });

    //         System.out.println("Queue " + queueName + " subscribed to Topic " + topicName);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

}
