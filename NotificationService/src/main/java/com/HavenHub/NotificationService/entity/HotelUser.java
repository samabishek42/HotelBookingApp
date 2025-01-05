package com.HavenHub.NotificationService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelUser {
      private int id;
      private String name;
      private String email;
      private String password;
      private String mobile;
      private String type;
      private String photo;


}
