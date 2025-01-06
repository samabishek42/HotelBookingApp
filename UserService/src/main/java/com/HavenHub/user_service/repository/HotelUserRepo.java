package com.HavenHub.user_service.repository;

import com.HavenHub.user_service.entity.HotelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelUserRepo extends JpaRepository<HotelUser,Integer> {

      HotelUser findByEmail(String email);

      HotelUser findByName(String name);

      HotelUser findById(int id);
}
