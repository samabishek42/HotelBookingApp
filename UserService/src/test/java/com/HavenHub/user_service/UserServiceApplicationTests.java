package com.HavenHub.user_service;

import com.HavenHub.user_service.entity.HotelUser;
import com.HavenHub.user_service.repository.HotelUserRepo;
import com.HavenHub.user_service.service.HotelUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

	@Mock
	private HotelUserRepo hotelUserRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private HotelUserService hotelUserService;



	@Test
	void testAddUser() {
		HotelUser user = new HotelUser( "John Doe", "john@example.com", "password","90483095", "user", "photo.png");
		when(hotelUserRepo.findByEmail(user.getEmail())).thenReturn(null);
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(hotelUserRepo.save(any(HotelUser.class))).thenReturn(user);

		String result = hotelUserService.addUser(user);

		assertEquals("John Doe", result);
		verify(hotelUserRepo, times(1)).save(any(HotelUser.class));
	}

	@Test
	void testAddAdmin() {
		HotelUser admin = new HotelUser( "Sam", "john@example.com", "password","90483095", "admin", "photo.png");
		when(hotelUserRepo.findByEmail(admin.getEmail())).thenReturn(null);
		when(passwordEncoder.encode(admin.getPassword())).thenReturn("encodedPassword");
		when(hotelUserRepo.save(any(HotelUser.class))).thenReturn(admin);

		String result = hotelUserService.addAdmin(admin);

		assertEquals("Sam", result);
		verify(hotelUserRepo, times(1)).save(any(HotelUser.class));
	}

	@Test
	void testGetUser() {
		HotelUser user = new HotelUser( "John Doe", "john@example.com", "password","90483095", "user", "photo.png");
		when(hotelUserRepo.findById(1)).thenReturn(user);

		HotelUser result = hotelUserService.getUser(1);

		assertEquals(user, result);
		verify(hotelUserRepo, times(1)).findById(1);
	}
}
