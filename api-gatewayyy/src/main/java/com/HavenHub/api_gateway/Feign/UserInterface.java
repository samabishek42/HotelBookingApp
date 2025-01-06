package com.HavenHub.api_gateway.Feign;

import com.HavenHub.api_gateway.entity.HotelUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient("USER-SERVICE")
public interface UserInterface {

      @GetMapping("/api/v1/user/getByEmail/{email}")
      ResponseEntity<HotelUser> getByEmail(@PathVariable("email") String email);

      @GetMapping("/api/v1/user/getByName/{name}")
      ResponseEntity<HotelUser> getByName(@PathVariable("name") String name);

      @PostMapping("/api/v1/user/save")
      ResponseEntity<String> saveUser(
              @RequestParam("name") String name,
              @RequestParam("email") String email,
              @RequestParam("password") String password,
              @RequestParam("mobile") String mobile,
              @RequestParam("type") String type,
              @RequestParam(value = "photo", required = false) String photoPath);

      @PostMapping("/api/v1/user/saveAdmin")
      ResponseEntity<String> saveAdmin(
              @RequestParam("name") String name,
              @RequestParam("email") String email,
              @RequestParam("password") String password,
              @RequestParam("mobile") String mobile,
              @RequestParam("type") String type,
              @RequestParam(value = "photo", required = false) String photoPath);

      @PostMapping("/api/v1/user/registerUser")
      ResponseEntity<String> registerUser(@RequestBody HotelUser user);

      @PostMapping("/api/v1/user/registerAdmin")
      ResponseEntity<String> registerAdmin(@RequestBody HotelUser user);

      @PostMapping("/api/v1/user/login")
      ResponseEntity<Map<String, String>> login(@RequestBody HotelUser user);

      @GetMapping("/api/v1/user/getOne/{id}")
      ResponseEntity<HotelUser> getOne(@PathVariable("id") int id);
}
