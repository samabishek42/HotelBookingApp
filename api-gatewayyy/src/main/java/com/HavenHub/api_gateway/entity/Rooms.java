package com.HavenHub.api_gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rooms {

      private int room_id;

      private int hotel_id;

      private int room_number;

      private String roomType;

      private int price;

      private String isAvailable;

      private String room_photo;


}
