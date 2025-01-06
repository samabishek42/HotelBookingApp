package com.HavenHub.hotel_service;

import com.HavenHub.hotel_service.DTO.HotelDTO;
import com.HavenHub.hotel_service.entity.Hotel;
import com.HavenHub.hotel_service.repository.HotelRepo;
import com.HavenHub.hotel_service.service.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceApplicationTests {

	@InjectMocks
	private HotelService hotelService;

	@Mock
	private HotelRepo hotelRepo;


	@Test
	void testAddHotel() {
		// Arrange
		HotelDTO hotelDTO = new HotelDTO("Haven Plaza", 4.5F, "123 Street", "Pool, Wi-Fi", "NYC", "photo.jpg", "1234567890", "123.456", 200);
		Hotel hotel = new Hotel("Haven Plaza", 4.5F, "123 Street", "Pool, Wi-Fi", "NYC", "photo.jpg", "1234567890", "123.456", 200);

		when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel);

		// Act
		String result = hotelService.addHotel(hotelDTO);

		// Assert
		assertEquals("Haven Plaza", result);
		verify(hotelRepo, times(1)).save(any(Hotel.class));
	}

	@Test
	void testGetHotels() {
		// Arrange
		List<Hotel> hotels = Arrays.asList(
			new Hotel("Hotel1", 4.2F, "Address1", "Features1", "City1", "Photo1", "Mobile1", "Location1", 150),
			new Hotel("Hotel2", 3.8F, "Address2", "Features2", "City2", "Photo2", "Mobile2", "Location2", 120)
		);
		when(hotelRepo.findAll()).thenReturn(hotels);

		// Act
		List<Hotel> result = hotelService.getHotels();

		// Assert
		assertEquals(2, result.size());
		verify(hotelRepo, times(1)).findAll();
	}

	@Test
	void testGetOnId() {
		// Arrange
		Hotel hotel = new Hotel("Hotel1", 4.2F, "Address1", "Features1", "City1", "Photo1", "Mobile1", "Location1", 150);
		when(hotelRepo.findById(1)).thenReturn(hotel);

		// Act
		Hotel result = hotelService.getOnId(1);

		// Assert
		assertEquals("Hotel1", result.getName());
		verify(hotelRepo, times(1)).findById(1);
	}

	@Test
	void testGetOnCity() {
		// Arrange
		List<Hotel> hotels = Arrays.asList(
			new Hotel("Hotel1", 4.2F, "Address1", "Pool", "NYC", "Photo1", "Mobile1", "Location1", 150)
		);
		when(hotelRepo.findByCity("NYC")).thenReturn(hotels);

		// Act
		List<Hotel> result = hotelService.getOnCity("NYC");

		// Assert
		assertEquals(1, result.size());
		assertEquals("NYC", result.get(0).getCity());
		verify(hotelRepo, times(1)).findByCity("NYC");
	}

	@Test
	void testDelete() {
		doNothing().when(hotelRepo).deleteById(1);//IT IS USED FOR AVOID EXCEPTION AND CLEAR TEST READABILITY
		// Act
		String result = hotelService.delete(1);
		// Assert
		assertEquals("success", result);
		verify(hotelRepo, times(1)).deleteById(1);
	}
}
