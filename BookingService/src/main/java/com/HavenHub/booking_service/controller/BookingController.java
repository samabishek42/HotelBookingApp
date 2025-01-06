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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

      @PostMapping(path = "/save/{user_id}")
      public ResponseEntity<String> saveBooking(@RequestBody BookingDTO booking, @PathVariable("user_id") int user_id) throws IOException {
            // Save the booking
           Booking booking1= bs.addBooking(booking);
            HotelUser user= ur.getOne(user_id).getBody();
            notificationRequest.setBooking(booking1);
            notificationRequest.setUser(user);
            try {
                  notificationInterface.booking(notificationRequest);
            } catch (Exception e) {
                  logger.error(e.getMessage());
            }
            return new ResponseEntity<>("Successfully Booked", HttpStatus.OK);
      }

      @GetMapping(path = "/getOne/{user_id}")
      public ResponseEntity<List<Booking>> getBookedRoomsByUser(@PathVariable("user_id") int user_id){
            List<Booking> list= bs.bookedRoomsByUser(user_id);
            if(list.isEmpty())
                  return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(list,HttpStatus.OK);
      }

      @GetMapping(path = "getAll/{hotel_id}/{check_in_date}")
      public ResponseEntity<List<Booking>> getAllRooms(@PathVariable("hotel_id") int hotel_id,
                                                       @PathVariable("check_in_date") LocalDate date){
            List<Booking> list=bs.getAllRoomsHotel(hotel_id,date);
            if(list.isEmpty())
                  return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(list,HttpStatus.OK);
      }

      @PutMapping(path = "/cancel/{booking_id}/{user_id}")
      public ResponseEntity<String> updateCancelled(@PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id) {
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
