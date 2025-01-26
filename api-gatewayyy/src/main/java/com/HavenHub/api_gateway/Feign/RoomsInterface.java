package com.HavenHub.api_gateway.Feign;


import com.HavenHub.api_gateway.entity.Rooms;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ROOMS-SERVICE")
public interface RoomsInterface {

      @PostMapping("/api/v1/rooms/save")
      ResponseEntity<String> addRoom(
              @RequestParam("hotel_id") int hotelId,
              @RequestParam("room_number") int roomNumber,
              @RequestParam("room_type") String roomType,
              @RequestParam("price") int price,
              @RequestParam("is_available") String isAvailable,
              @RequestParam("room_photo") String photoPath
      );

      @GetMapping("/api/v1/rooms/getAllRooms/{hotel_id}")
      ResponseEntity<List<Rooms>> getAllRooms(@PathVariable("hotel_id") int hotelId);

}
