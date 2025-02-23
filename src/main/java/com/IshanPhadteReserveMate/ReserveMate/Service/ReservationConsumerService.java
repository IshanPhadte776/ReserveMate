// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.integration.core.MessageHandler;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.cloud.stream.function.StreamBridge;
// import org.springframework.integration.channel.DirectChannel;
// import org.springframework.stereotype.Component;

// import com.IshanPhadteReserveMate.ReserveMate.Model.Reservation;

// @Component
// public class ReservationConsumer {

//     @Autowired
//     private StreamBridge streamBridge;

//     // Define a DirectChannel (SubscribableChannel)
//     private final MessageChannel inputChannel = new DirectChannel();

//     public ReservationConsumer() {
//         // Add the message handler to process incoming messages
//         inputChannel.subscribe(new MessageHandler() {
//             @Override
//             public void handleMessage(Message<?> message) {
//                 // Process the incoming reservation message
//                 Reservation reservation = (Reservation) message.getPayload();
//                 String phoneNumber = reservation.getPhoneNumber();

//                 // Create dynamic topic for the user
//                 String userTopic = "reservation/" + phoneNumber;

//                 // Send the reservation to the specific user's topic
//                 sendReservationToUserTopic(userTopic, reservation);
//             }
//         });
//     }

//     public void sendReservationToUserTopic(String topic, Reservation reservation) {
//         // Send reservation to the user-specific topic using StreamBridge
//         streamBridge.send(topic, reservation);
//     }

//     public void processMessage(Message<?> message) {
//         // Manually send the message to the input channel
//         inputChannel.send(message);
//     }
// }
