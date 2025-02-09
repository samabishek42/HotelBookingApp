package com.HavenHub.booking_service;

import com.HavenHub.booking_service.DTO.BookingDTO;
import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.repository.BookingRepo;
import com.HavenHub.booking_service.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BookingServiceIntegrationTest  {

      @Autowired
      private BookingService bookingService;

      @Autowired
      private BookingRepo bookingRepo;

      @BeforeEach
      void clearDatabase() {
            bookingRepo.deleteAll();
      }

      @Test
      void testAddBooking() {
            // Arrange
            BookingDTO bookingDTO = new BookingDTO(
                    1, LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                    "booked", 5000, "Paid", 101, 2, 3
            );

            // Act
            Booking result = bookingService.addBooking(bookingDTO);

            // Assert
            assertNotNull(result);
            assertEquals(101, result.getHotel_id());
            assertEquals("booked", result.getStatus());
            assertEquals(5000, result.getTotalAmount());
      }

      @Test
      void testBookedRoomsByUser() {
            // Arrange
            Booking booking = new Booking(1, 101, 2, 3, LocalDate.now(),
                    LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                    "booked", 5000, "Paid");

            bookingRepo.save(booking);

            // Act
            List<Booking> result = bookingService.bookedRoomsByUser(1);

            // Assert
            assertEquals(1, result.size());
            assertEquals(1, result.get(0).getUser_id());
            assertEquals("booked", result.get(0).getStatus());
      }

      @Test
      void testUpdateBooking() {
            // Arrange
            Booking booking = new Booking(1, 101, 2, 3, LocalDate.now(),
                    LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                    "booked", 5000, "Paid");

            bookingRepo.save(booking);

            // Act
            Booking result = bookingService.update(1);

            // Assert
            assertEquals("cancelled", result.getStatus());
            assertEquals(5000, result.getTotalAmount());
      }

      @Test
      void testGetAllRoomsHotel() {
            // Arrange
            LocalDate checkInDate = LocalDate.now().plusDays(1);
            Booking booking = new Booking(1, 101, 2, 3, LocalDate.now(),
                    checkInDate, LocalDate.now().plusDays(3),
                    "booked", 5000, "Paid");

            bookingRepo.save(booking);

            // Act
            List<Booking> result = bookingService.getAllRoomsHotel(101, checkInDate);

            // Assert
            assertEquals(1, result.size());
            assertEquals(101, result.get(0).getHotel_id());
            assertEquals(checkInDate, result.get(0).getCheckInDate());
      }
}
