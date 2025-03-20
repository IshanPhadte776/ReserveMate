// package com.IshanPhadteReserveMate.ReserveMate.Config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jms.connection.CachingConnectionFactory;
// import com.solacesystems.jms.SolConnectionFactory;

// @Configuration
// public class JMSConfig {

//     @Bean
//     public javax.jms.ConnectionFactory connectionFactory() {
//         SolConnectionFactory connectionFactory = new SolConnectionFactory();
//         connectionFactory.setHost("your-solace-host");
//         connectionFactory.setUsername("your-username");
//         connectionFactory.setPassword("your-password");
//         return new CachingConnectionFactory(connectionFactory);
//     }
// }
