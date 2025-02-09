package com.HavenHub.booking_service.Feign;

import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.entity.HotelUser;
import com.HavenHub.booking_service.entity.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATION-SERVICE")
public interface NotificationInterface {

      @PostMapping(path = "api/v1/notification/cancel")
      void  cancel(@RequestBody HotelUser user);

      @PostMapping(path = "api/v1/notification/booking")
      void  booking(@RequestBody NotificationRequest notificationRequest);
}
