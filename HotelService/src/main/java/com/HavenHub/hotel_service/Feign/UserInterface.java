package com.HavenHub.hotel_service.Feign;

import com.HavenHub.hotel_service.entity.HotelUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")

public interface UserInterface {


      @GetMapping("api/v1/user/getByEmail/{email}")
      public ResponseEntity<HotelUser> getByEmail(@PathVariable("email") String email);

      @GetMapping("api/v1/user/getByName/{name}")
      public ResponseEntity<HotelUser> getByName(@PathVariable("name") String name);


}
