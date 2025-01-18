package com.HavenHub.NotificationService.Feign;


import com.HavenHub.NotificationService.entity.HotelUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")

public interface UserInterface {


      @GetMapping("api/v1/user/getByEmail/{email}")
      ResponseEntity<HotelUser> getByEmail(@PathVariable("email") String email);

      @GetMapping("api/v1/user/getByName/{name}")
      ResponseEntity<HotelUser> getByName(@PathVariable("name") String name);

      @GetMapping("api/v1/user/getOne/{id}")
      ResponseEntity<HotelUser> getOne(@PathVariable("id") int id);

}
