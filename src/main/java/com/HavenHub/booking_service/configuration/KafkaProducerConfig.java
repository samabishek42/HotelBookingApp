package com.HavenHub.booking_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

      @Bean
      public NewTopic createTopic(){
            return new NewTopic("booking-notification",4,(short)1);
      }
}
