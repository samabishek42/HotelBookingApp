package com.HavenHub.rooms_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomsDTO {


      private int hotel_id;

      private int room_number;

      private String roomType;

      private int price;

      private String isAvailable;

      private String room_photo;


}
