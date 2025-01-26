package com.HavenHub.api_gateway.Feign;


import com.HavenHub.api_gateway.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelInterface {

      @PostMapping("/api/v1/hotel/save")
      ResponseEntity<String> addHotel(
              @RequestParam("name") String name,
              @RequestParam("ratings") float ratings,
              @RequestParam("address") String address,
              @RequestParam("features") String features,
              @RequestParam("city") String city,
              @RequestParam("mobile") String mobile,
              @RequestParam("location") String location,
              @RequestParam("photo") String photoPath,
              @RequestParam("price") int price
      );

      @GetMapping("/api/v1/hotel/getAllHotels")
      ResponseEntity<List<Hotel>> getAllHotels();

      @GetMapping("/api/v1/hotel/getOne/{id}")
      ResponseEntity<Hotel> getHotelById(@PathVariable("id") int id);

      @GetMapping("/api/v1/hotel/getOneHotel/{city}")
      ResponseEntity<List<Hotel>> getHotelsByCity(@PathVariable("city") String city);

      @DeleteMapping("/api/v1/hotel/deleteHotel/{id}")
      ResponseEntity<String> deleteHotel(@PathVariable("id") int id);
}
