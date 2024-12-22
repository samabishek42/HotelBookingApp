package com.HavenHub.hotel_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
public class Hotel {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "hotel_id")
      private int id;

      @Column(name = "name")
      private String name;

      @Column(name = "ratings")
      private float ratings;

      @Column(name = "address")
      private String address;

      @Column(name = "features")
      private String features;

      @Column(name = "city")
      private String city;

      @Column(name = "hotel_photo",length = 500)
      private String hotel_photo;

      @Column(name = "mobile")
      private String mobile;

      @Column(name = "location",length = 500)
      private String location;

      @Column(name = "price")
      private int price;

      // No reference to Booking or Rooms entities


      public Hotel(String name, float ratings, String address, String features, String city,
                   String hotel_photo, String mobile, String location, int price) {
            this.name = name;
            this.ratings = ratings;
            this.address = address;
            this.features = features;
            this.city = city;
            this.hotel_photo = hotel_photo;
            this.mobile = mobile;
            this.location = location;
            this.price = price;
      }
}
