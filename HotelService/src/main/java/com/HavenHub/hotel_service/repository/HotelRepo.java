package com.HavenHub.hotel_service.repository;

import com.HavenHub.hotel_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface HotelRepo extends JpaRepository<Hotel,Integer> {
      List<Hotel> findAll();

      Hotel findById(int id);

      List<Hotel> findByCity(String city);

}
