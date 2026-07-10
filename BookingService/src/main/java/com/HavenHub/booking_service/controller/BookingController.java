package com.HavenHub.booking_service.controller;

import com.HavenHub.booking_service.DTO.BookingDTO;
import com.HavenHub.booking_service.Feign.NotificationInterface;
import com.HavenHub.booking_service.Feign.UserInterface;
import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.entity.HotelUser;
import com.HavenHub.booking_service.entity.NotificationRequest;
import com.HavenHub.booking_service.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/booking")
public class BookingController {

      @Autowired
      BookingService bs;

      @Autowired
      UserInterface ur;

      @Autowired
      NotificationRequest notificationRequest;

      // Twilio credentials from your dashboard
      public static final String ACCOUNT_SID = "AC340bbd34c29132b914e8f013f3037da4"; // Replace with your Account SID
      public static final String AUTH_TOKEN = "49f45c04a9b233194136b4f10c8192ea";   // Replace with your Auth Token

      private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

      @Autowired
      NotificationInterface notificationInterface;

      @Autowired
      private ThreadPoolTaskScheduler taskScheduler; // Email service to send email notifications

      @Autowired
      private KafkaTemplate<String, NotificationRequest> kafkaTemplate;
//
//      @Autowired
//      KafkaTemplate<String, String> template;

      @PostMapping(path = "/save/{user_id}")
      public ResponseEntity<String> saveBooking(@RequestBody BookingDTO booking, @PathVariable("user_id") int user_id) throws Exception {

            Booking booking1= bs.addBooking(booking);
            HotelUser user= ur.getOne(user_id).getBody();
            System.out.println(user);
            notificationRequest.setBooking(booking1);
            notificationRequest.setUser(user);
            try {
//                 notificationInterface.booking(notificationRequest);
                   //Send to Kafka asynchronously
                  kafkaTemplate.send("booking-notification", notificationRequest); // Send message to Kafka topic
                  logger.info("Booking notification message sent to Kafka");
            } catch (Exception e) {
                  logger.error(e.getMessage());
            }

            return new ResponseEntity<>("Successfully Booked", HttpStatus.OK);
      }

      @GetMapping(path = "/getOne/{user_id}")
      public ResponseEntity<List<Booking>> getBookedRoomsByUser(@PathVariable("user_id") int user_id) throws Exception {

            List<Booking> list= bs.bookedRoomsByUser(user_id);
            if(list.isEmpty())
                  return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(list,HttpStatus.OK);
      }

//      @GetMapping("/check")
//      public void check(){
//            logger.info("Kafka message produced");
//
//            template.send("booking-notification","Message sent");
//      }

      @GetMapping(path = "getAll/{hotel_id}/{check_in_date}")
      public ResponseEntity<List<Booking>> getAllRooms(
              @PathVariable("hotel_id") int hotel_id,
              @PathVariable("check_in_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate) throws Exception {


            logger.info(String.valueOf(checkInDate));
            System.out.println(checkInDate);
            // Call the service with the processed date
            List<Booking> list = bs.getAllRoomsHotel(hotel_id,checkInDate);

            // Log the bookings
            for (Booking booking : list) {
                  logger.info(String.valueOf(booking));
            }

            // Return response based on the result
            if (list.isEmpty()) {
                  return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
            }

            System.out.println("Success");
            return new ResponseEntity<>(list, HttpStatus.OK);
      }

      private LocalDate processDate(LocalDate inputDate) {
            // Example: Modify the date or log it as needed
            logger.info("Processing input date: " + inputDate);

            // Returning the input date as-is (or modify if required)
            return inputDate;
      }

      @PutMapping(path = "/cancel/{booking_id}/{user_id}")
      public ResponseEntity<String> updateCancelled(@PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id) throws Exception{

            Booking booking = bs.update(booking_id);
            HotelUser user= ur.getOne(user_id).getBody(); 
            try {
                  notificationInterface.cancel(user);
            } catch (Exception e) {
                  logger.error(e.getMessage());
            }
            return new ResponseEntity<>("Success", HttpStatus.OK);
      }


}
