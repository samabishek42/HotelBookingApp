package com.HavenHub.rooms_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
public class Rooms implements Serializable {

      private static final long serialVersionUID = 2L;


      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "room_id")
      private int room_id;

      @Column(name = "hotel_id", nullable = false)  // Foreign key reference to Hotel.
      private int hotel_id;

      @Column(name = "room_number")
      private int room_number;

      @Column(name = "room_type")
      private String roomType;

      @Column(name = "price")
      private int price;

      @Column(name = "is_available")
      private String isAvailable;

      @Column(name = "room_photo")
      private String room_photo;

      public Rooms( int hotel_id, int room_number, String roomType,
                   int price, String isAvailable, String room_photo) {
            this.hotel_id = hotel_id;
            this.room_number = room_number;
            this.roomType = roomType;
            this.price = price;
            this.isAvailable = isAvailable;
            this.room_photo = room_photo;
      }
}
