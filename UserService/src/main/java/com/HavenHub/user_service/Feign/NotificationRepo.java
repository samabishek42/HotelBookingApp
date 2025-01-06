package com.HavenHub.user_service.Feign;

import com.HavenHub.user_service.DTO.HotelUserDTO;
import com.HavenHub.user_service.entity.HotelUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATION-SERVICE")
public interface NotificationRepo {

      @PostMapping("/api/v1/notification/register")
      void save(@RequestBody HotelUser user);


}
