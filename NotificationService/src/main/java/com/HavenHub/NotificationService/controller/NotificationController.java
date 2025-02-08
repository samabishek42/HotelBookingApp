package com.HavenHub.NotificationService.controller;

import com.HavenHub.NotificationService.Feign.UserInterface;
import com.HavenHub.NotificationService.entity.Booking;
import com.HavenHub.NotificationService.entity.HotelUser;
import com.HavenHub.NotificationService.entity.NotificationRequest;
import com.HavenHub.NotificationService.service.EmailService;
import com.HavenHub.NotificationService.service.NotificationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/notification")
@EnableKafka
public class NotificationController {

      @Autowired
      private EmailService emailService;

      @Autowired
      private UserInterface ur;

      @Autowired
      private ThreadPoolTaskScheduler taskScheduler;

      @Autowired
      NotificationService notificationService;


      private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);


      // Twilio credentials from your dashboard
      public static final String ACCOUNT_SID = "AC340bbd34c29132b914e8f013f3037da4"; // Replace with your Account SID
      public static final String AUTH_TOKEN = "c69023196ba79ca98f58edd7d3ad5187";   // Replace with your Auth Token


      @PostMapping("/register")
      public void save(@RequestBody HotelUser user) {

          notificationService.registerEmail(user);

          notificationService.registerSMS(user);
      }

//      @KafkaListener(topics = "booking-notification", groupId = "group")
//      public void check(String string){
//            logger.info(string);
//      }


//
//      @PostMapping(path = "/booking")
//      @KafkaListener(topicPartitions = @TopicPartition(
//            topic = "booking-notification",
//            partitions = {"0", "1", "2","3"}  // Change according to the actual partitions
//            ),  groupId = "notification-group")
      @KafkaListener(topics="booking-notification",groupId = "notification-group")
      public void booking(NotificationRequest notificationRequest) {

                  notificationService.bookingEmail(notificationRequest);

                  notificationService.bookingSMS(notificationRequest.getUser());
      }


      @PostMapping(path = "/cancel")
      public void cancel(@RequestBody HotelUser user) {
            notificationService.cancelEmail(user);
      }


}