package com.HavenHub.rooms_service;

import com.HavenHub.rooms_service.DTO.RoomsDTO;
import com.HavenHub.rooms_service.entity.Rooms;
import com.HavenHub.rooms_service.repository.RoomsRepo;
import com.HavenHub.rooms_service.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomsServiceApplicationTests {

	@Mock
	private RoomsRepo roomsRepo;

	@InjectMocks
	private RoomService roomService;



	@Test
	void testAddRooms() {
		RoomsDTO roomsDTO = new RoomsDTO(1, 101, "Single", 5000, "yes", "room.jpg");
		Rooms room = new Rooms(1, 101, "Single", 5000, "yes", "room.jpg");
		when(roomsRepo.save(any(Rooms.class))).thenReturn(room);

		String result = roomService.addRooms(roomsDTO);

		assertEquals("Success", result);
		verify(roomsRepo, times(1)).save(any(Rooms.class));
	}

	@Test
	void testGetRooms() {
		Rooms room1 = new Rooms(1, 101, "Single", 5000, "yes", "room.jpg");
		Rooms room2 = new Rooms(1, 102, "Double", 8000, "yes", "room2.jpg");
		List<Rooms> roomsList = Arrays.asList(room1, room2);
		when(roomsRepo.findByHotelId(1)).thenReturn(roomsList);

		List<Rooms> result = roomService.getRooms(1);

		assertEquals(roomsList, result);
		verify(roomsRepo, times(1)).findByHotelId(1);
	}
}
