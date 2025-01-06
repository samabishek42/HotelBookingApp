package com.HavenHub.api_gateway.Feign;

import com.HavenHub.api_gateway.entity.Booking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient("BOOKING-SERVICE")
public interface BookingInterface {

      @PostMapping("/api/v1/booking/save/{user_id}")
      ResponseEntity<String>  saveBooking(@RequestBody Booking booking, @PathVariable("user_id") int user_id);

      @GetMapping("/api/v1/booking/getOne/{user_id}")
      ResponseEntity<List<Booking>> getBookedRoomsByUser(@PathVariable("user_id") int userId);

      @GetMapping("/api/v1/booking/getAll/{hotel_id}/{check_in_date}")
      ResponseEntity<List<Booking>> getAllRooms(
              @PathVariable("hotel_id") int hotelId,
              @PathVariable("check_in_date") LocalDate date
      );

      @PutMapping("/api/v1/booking/cancel/{booking_id}/{user_id}")
      ResponseEntity<String> updateCancelled(@PathVariable("booking_id") int bookingId, @PathVariable("user_id") int user_id);
}
