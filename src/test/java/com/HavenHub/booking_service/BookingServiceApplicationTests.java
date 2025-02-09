package com.HavenHub.booking_service;

import com.HavenHub.booking_service.DTO.BookingDTO;
import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.repository.BookingRepo;
import com.HavenHub.booking_service.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceApplicationTests {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private BookingRepo bookingRepo;



	@Test
	void testAddBooking() {
		// Arrange
		BookingDTO bookingDTO = new BookingDTO(
			1, LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
			"booked", 5000, "Paid", 101, 2, 3
		);

		Booking booking = new Booking(1, 101, 2, 3, LocalDate.now(),
			LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
			"booked", 5000, "Paid");

		when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

		// Act
		Booking b= bookingService.addBooking(bookingDTO);

		// Assert
		assertEquals(booking, b);
		verify(bookingRepo, times(1)).save(any(Booking.class));
	}

	@Test
	void testBookedRoomsByUser() {
		// Arrange
		List<Booking> bookings = Arrays.asList(
			new Booking(1, 101, 2, 1, LocalDate.now(),
				LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
				"booked", 3000, "Paid")
		);

		when(bookingRepo.findByUserId(1)).thenReturn(bookings);

		// Act
		List<Booking> result = bookingService.bookedRoomsByUser(1);

		// Assert
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getUser_id());
		verify(bookingRepo, times(1)).findByUserId(1);
	}

	@Test
	void testUpdateBooking() {
		// Arrange
		Booking booking = new Booking(1, 101, 2, 1, LocalDate.now(),
			LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
			"booked", 3000, "Paid");

		when(bookingRepo.findByBooking_id(1)).thenReturn(booking);
		when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

		// Act
	Booking b= bookingService.update(1);

		// Assert
		assertEquals(booking, b);
		assertEquals("cancelled", booking.getStatus());
		verify(bookingRepo, times(1)).findByBooking_id(1);
		verify(bookingRepo, times(1)).save(booking);
	}

	@Test
	void testGetAllRoomsHotel() {
		// Arrange
		LocalDate checkInDate = LocalDate.now().plusDays(1);
		List<Booking> bookings = Arrays.asList(
			new Booking(1, 101, 2, 1, LocalDate.now(),
				checkInDate, LocalDate.now().plusDays(3),
				"booked", 3000, "Paid")
		);

		when(bookingRepo.findByHotel_idAndCheckInDate(101, checkInDate)).thenReturn(bookings);

		// Act
		List<Booking> result = bookingService.getAllRoomsHotel(101, checkInDate);

		// Assert
		assertEquals(1, result.size());
		assertEquals(101, result.get(0).getHotel_id());
		assertEquals(checkInDate, result.get(0).getCheckInDate());
		verify(bookingRepo, times(1)).findByHotel_idAndCheckInDate(101, checkInDate);
	}
}
