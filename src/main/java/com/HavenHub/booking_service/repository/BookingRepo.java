package com.HavenHub.booking_service.repository;

import com.HavenHub.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

      @Query("SELECT b FROM Booking b WHERE b.user_id = :userId")
      List<Booking> findByUserId(@Param("userId") int userId);

      @Query("SELECT b FROM Booking b WHERE b.booking_id = :id")
      Booking findByBooking_id(@Param("id") int id);

      @Query("SELECT b FROM Booking b WHERE b.hotel_id = :hotel_id AND b.checkInDate = :date")
      List<Booking> findByHotel_idAndCheckInDate(@Param("hotel_id") int hotel_id,
                                                 @Param("date") LocalDate date);

}
