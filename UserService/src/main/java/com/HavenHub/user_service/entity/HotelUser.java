package com.HavenHub.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class HotelUser {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "user_id")
      private int id;

      @Column(name = "username")
      private String name;

      @Column(name = "email", nullable = false, unique = true)
      private String email;

      @Column(name = "password")
      private String password;

      @Column(name = "mobile")
      private String mobile;

      @Column(name = "type")
      private String type;

      @Column(name = "photo")
      private String photo;


      // No reference to Booking entity
      public HotelUser(String name, String email, String password, String mobile, String type, String photo) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
            this.type = type;
            this.photo = photo;
      }
}
