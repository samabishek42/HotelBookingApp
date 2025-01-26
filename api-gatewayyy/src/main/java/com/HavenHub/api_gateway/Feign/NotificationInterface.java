package com.HavenHub.api_gateway.Feign;

import com.HavenHub.api_gateway.entity.HotelUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="NOTIFICATION-SERVICE")
public interface NotificationInterface {

      @PostMapping("/api/v1/notification/register")
      void save(@RequestBody HotelUser user);


}
