package com.HavenHub.booking_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

      private int user_id;

      private LocalDate bookingDate;

      private LocalDate checkInDate;

      private  LocalDate checkOutDate;

      private String status;

      private int TotalAmount;

      private String payments;

      private int hotel_id;

      private int single_room;

      private int double_room;

}
