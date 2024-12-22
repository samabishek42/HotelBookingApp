package com.HavenHub.rooms_service.repository;

import com.HavenHub.rooms_service.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface RoomsRepo extends JpaRepository<Rooms,Integer> {

      @Query("SELECT r FROM Rooms r WHERE r.hotel_id = :id")
      List<Rooms> findByHotelId(@Param("id") int id);

      @Query("SELECT r FROM Rooms r WHERE r.room_id = :id")
      Rooms findByRoom_id(@Param("id") int id);
}
