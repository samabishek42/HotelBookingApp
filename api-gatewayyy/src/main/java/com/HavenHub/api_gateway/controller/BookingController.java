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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/booking")
public class BookingController {

      @Autowired
      BookingInterface bs;

      @PostMapping(path = "/save/{user_id}")
      @CircuitBreaker(name = "saveBookingService", fallbackMethod = "saveBookingFallback")
      public CompletableFuture<ResponseEntity<String>> saveBooking(@RequestBody Booking booking, @PathVariable("user_id") int user_id) throws Exception {
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> bs.saveBooking(booking, user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                         throw new RuntimeException("Timeout occurred", ex);
                    });
      }

      // Fallback Method
      public CompletableFuture<ResponseEntity<String>> saveBookingFallback(Exception e,@RequestBody Booking booking, @PathVariable("user_id") int user_id) {
            return CompletableFuture.supplyAsync(() -> bs.saveBooking(booking, user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Booking service is unavailable.");
                    });
      }



      @GetMapping(path = "/getOne/{user_id}")
      @CircuitBreaker(name = "getBookedRoomsByUserService",fallbackMethod = "getBookedRoomsByUserFallback")
      public CompletableFuture<ResponseEntity<List<Booking>>> getBookedRoomsByUser(@PathVariable("user_id") int user_id) {
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> bs.getBookedRoomsByUser(user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          throw new RuntimeException("Timeout occurred", ex);
                    });
      }

      // Fallback Method
      public  CompletableFuture<ResponseEntity<List<Booking>>> getBookedRoomsByUserFallback(Exception e, @PathVariable("user_id") int user_id) {
            return CompletableFuture.supplyAsync(() -> bs.getBookedRoomsByUser(user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                  .body(Collections.emptyList());
                    });
      }


      @GetMapping(path = "getAll/{hotel_id}/{check_in_date}")
      @CircuitBreaker(name = "getAllRoomsService",fallbackMethod = "getAllRoomsFallback")
      public CompletableFuture<ResponseEntity<List<Booking>>> getAllRooms(@PathVariable("hotel_id") int hotel_id,
                                                       @PathVariable("check_in_date") LocalDate date) throws Exception{
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> bs.getAllRooms(hotel_id,date))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          throw new RuntimeException("Timeout occurred", ex);
                    });
      }


      // Fallback Method
      public CompletableFuture<ResponseEntity<List<Booking>>> getAllRoomsFallback(@PathVariable("hotel_id") int hotel_id,
                                                       @PathVariable("check_in_date") LocalDate date)  {
            return CompletableFuture.supplyAsync(() -> bs.getAllRooms(hotel_id,date))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                  .body(Collections.emptyList());
                    });
      }


      @PutMapping(path = "/cancel/{booking_id}/{user_id}")
      @CircuitBreaker(name = "updateCancelledService",fallbackMethod = "updateCancelledFallback")
      public CompletableFuture<ResponseEntity<String>>  updateCancelled(@PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id) throws Exception {
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> bs.updateCancelled(booking_id,user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          throw new RuntimeException("Timeout occurred", ex);
                    });
      }

      // Fallback Method
      public CompletableFuture<ResponseEntity<String>> updateCancelledFallback(Exception e,@PathVariable("booking_id") int booking_id, @PathVariable("user_id") int user_id) {
            return CompletableFuture.supplyAsync(() -> bs.updateCancelled(booking_id, user_id))
                    .orTimeout(6, TimeUnit.SECONDS) // Timeout after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());
                          return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Booking service is unavailable.");
                    });
      }


}
