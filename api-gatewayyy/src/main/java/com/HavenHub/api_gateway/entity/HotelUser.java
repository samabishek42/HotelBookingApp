package com.HavenHub.api_gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
