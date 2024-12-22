package com.HavenHub.booking_service.entity;
//Data Transfer Object

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

      private int id;
      private String name;
      private float ratings;
      private String address;
      private String features;
      private String city;
      private String hotel_photo;
      private String mobile;
      private String location;
      private int price;

}
