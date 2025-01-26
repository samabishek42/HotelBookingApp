package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.BookingInterface;
import com.HavenHub.api_gateway.entity.Booking;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/booking")
public class BookingController {

      @Autowired
      BookingInterface bs;

      // Save Booking
      @PostMapping(path = "/save/{user_id}")
      @CircuitBreaker(name = "saveBookingService", fallbackMethod = "saveBookingFallback")
      public ResponseEntity<String> saveBooking(@RequestBody Booking booking, @PathVariable("user_id") int user_id) throws Exception {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ResponseEntity<String>> future = executor.submit(() -> bs.saveBooking(booking, user_id));

            try {
                  return future.get(6, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception e) {
                  System.err.println("Timeout or error occurred: " + e.getMessage());
                  throw new RuntimeException("Booking service timed out or failed", e);
            }
      }

      // Fallback Method for saveBooking
      public ResponseEntity<String> saveBookingFallback(Exception e, @RequestBody Booking booking, @PathVariable("user_id") int user_id) {

            try {

                  ExecutorService executor = Executors.newSingleThreadExecutor();
                  Future<ResponseEntity<String>> future = executor.submit(() -> bs.saveBooking(booking, user_id));

                  return future.get(4, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception ex) {
                  System.err.println("Timeout or error occurred in fallback: " + ex.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Booking service is unavailable.");
            }
      }

      // Get Booked Rooms by User
      @GetMapping(path = "/getOne/{user_id}")
      @CircuitBreaker(name = "getBookedRoomsByUserService", fallbackMethod = "getBookedRoomsByUserFallback")
      public ResponseEntity<List<Booking>> getBookedRoomsByUser(@PathVariable("user_id") int user_id) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ResponseEntity<List<Booking>>> future = executor.submit(() -> bs.getBookedRoomsByUser(user_id));

            try {
                  return future.get(6, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception e) {
                  System.err.println("Timeout or error occurred: " + e.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
            }
      }

      // Fallback Method for getBookedRoomsByUser
      public ResponseEntity<List<Booking>> getBookedRoomsByUserFallback(Exception e, @PathVariable("user_id") int user_id) {

            try {

                  ExecutorService executor = Executors.newSingleThreadExecutor();
                  Future<ResponseEntity<List<Booking>>> future = executor.submit(() -> bs.getBookedRoomsByUser(user_id));

                  return future.get(4, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception ex) {
                  System.err.println("Timeout or error occurred in fallback: " + ex.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
            }
      }

      // Get All Rooms for a Hotel on a Specific Date
      @GetMapping(path = "getAll/{hotel_id}/{check_in_date}")
      @CircuitBreaker(name = "getAllRoomsService", fallbackMethod = "getAllRoomsFallback")
      public ResponseEntity<List<Booking>> getAllRooms(@PathVariable("hotel_id") int hotel_id,
                                                       @PathVariable("check_in_date") LocalDate date) throws Exception {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ResponseEntity<List<Booking>>> future = executor.submit(() -> bs.getAllRooms(hotel_id, date));

            try {
                  return future.get(6, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception e) {
                  System.err.println("Timeout or error occurred: " + e.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
            }
      }

      // Fallback Method for getAllRooms
      public ResponseEntity<List<Booking>> getAllRoomsFallback(@PathVariable("hotel_id") int hotel_id,
                                                               @PathVariable("check_in_date") LocalDate date) {

            try {

                  ExecutorService executor = Executors.newSingleThreadExecutor();
                  Future<ResponseEntity<List<Booking>>> future = executor.submit(() -> bs.getAllRooms(hotel_id, date));

                  return future.get(4, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception ex) {
                  System.err.println("Timeout or error occurred in fallback: " + ex.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
            }
      }

      // Update Cancelled Booking
      @PutMapping(path = "/cancel/{booking_id}/{user_id}")
      @CircuitBreaker(name = "updateCancelledService", fallbackMethod = "updateCancelledFallback")
      public ResponseEntity<String> updateCancelled(@PathVariable("booking_id") int booking_id,
                                                    @PathVariable("user_id") int user_id) throws Exception {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ResponseEntity<String>> future = executor.submit(() -> bs.updateCancelled(booking_id, user_id));

            try {
                  return future.get(6, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception e) {
                  System.err.println("Timeout or error occurred: " + e.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Booking service is unavailable.");
            }
      }

      // Fallback Method for updateCancelled
      public ResponseEntity<String> updateCancelledFallback(Exception e, @PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id) {

            try {

                  ExecutorService executor = Executors.newSingleThreadExecutor();
                  Future<ResponseEntity<String>> future = executor.submit(() -> bs.updateCancelled(booking_id, user_id));

                  return future.get(4, TimeUnit.SECONDS);  // Timeout after 6 seconds
            } catch (Exception ex) {
                  System.err.println("Timeout or error occurred in fallback: " + ex.getMessage());
                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Booking service is unavailable.");
            }
      }


}
