package com.HavenHub.booking_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Booking {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "booking_id")
      private int booking_id;

      @Column(name = "user_id", nullable = false)  // Foreign key reference to User.
      private int user_id;

      @Column(name = "hotel_id", nullable = false)  // Foreign key reference to Hotel.
      private int hotel_id;

      @Column(name = "single_room")
      private int single_room;

      @Column(name = "double_room")
      private int double_room;

      @Column(name = "booking_date")
      private LocalDate bookingDate;

      @Column(name = "check_in_date")
      private LocalDate checkInDate;

      @Column(name = "check_out_date")
      private LocalDate checkOutDate;

      @Column(name = "status")
      private String status;

      @Column(name = "total_amount")
      private int totalAmount;

      @Column(name = "payments")
      private String payments;

      public Booking( int user_id, int hotel_id, int single_room,
                     int double_room, LocalDate bookingDate, LocalDate checkInDate,
                     LocalDate checkOutDate, String status, int totalAmount, String payments) {
            this.user_id = user_id;
            this.hotel_id = hotel_id;
            this.single_room = single_room;
            this.double_room = double_room;
            this.bookingDate = bookingDate;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.status = status;
            this.totalAmount = totalAmount;
            this.payments = payments;
      }
}
