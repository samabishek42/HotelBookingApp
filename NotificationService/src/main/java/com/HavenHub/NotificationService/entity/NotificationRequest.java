package com.HavenHub.NotificationService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class NotificationRequest{
      private HotelUser user;
      private Booking booking;

      // getters and setters
}
