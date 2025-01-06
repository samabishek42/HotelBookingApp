package com.HavenHub.booking_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class NotificationRequest {
      private HotelUser user;
      private Booking booking;

      // getters and setters
}
