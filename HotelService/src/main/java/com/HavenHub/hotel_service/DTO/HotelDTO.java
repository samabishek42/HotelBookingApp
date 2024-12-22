package com.HavenHub.hotel_service.DTO;
//Data Transfer Object

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {


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
