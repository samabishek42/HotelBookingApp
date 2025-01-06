package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.BookingInterface;
import com.HavenHub.api_gateway.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/booking")
public class BookingController {

      @Autowired
      BookingInterface bs;

      @PostMapping(path="/save/{user_id}")
      public ResponseEntity<String> saveBooking(@RequestBody Booking booking, @PathVariable("user_id") int user_id){
            return bs.saveBooking(booking,user_id);
      }

      @GetMapping(path = "/getOne/{user_id}")
      public ResponseEntity<List<Booking>> getBookedRoomsByUser(@PathVariable("user_id") int user_id){
            return bs.getBookedRoomsByUser(user_id);
      }

      @GetMapping(path = "getAll/{hotel_id}/{check_in_date}")
      public ResponseEntity<List<Booking>> getAllRooms(@PathVariable("hotel_id") int hotel_id,
                                                       @PathVariable("check_in_date") LocalDate date){
            return bs.getAllRooms(hotel_id,date);
      }

      @PutMapping(path = "/cancel/{booking_id}/{user_id}")
      public ResponseEntity<String>  updateCancelled(@PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id){
            return bs.updateCancelled(booking_id,user_id);
      }



}
