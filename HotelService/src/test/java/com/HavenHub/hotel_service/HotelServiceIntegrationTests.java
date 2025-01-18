package com.HavenHub.hotel_service;
import com.HavenHub.hotel_service.DTO.HotelDTO;
import com.HavenHub.hotel_service.entity.Hotel;
import com.HavenHub.hotel_service.repository.HotelRepo;
import com.HavenHub.hotel_service.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class HotelServiceIntegrationTests {

      @Autowired
      private HotelService hotelService;

      @Autowired
      private HotelRepo hotelRepo;

      @BeforeEach
      void clearDatabase() {
            hotelRepo.deleteAll();
      }

      @Test
      void testAddHotel() {
            // Arrange
            HotelDTO hotelDTO = new HotelDTO("Haven Plaza", 4.5F, "123 Street", "Pool, Wi-Fi", "NYC", "photo.jpg", "1234567890", "123.456", 200);

            // Act
            String result = hotelService.addHotel(hotelDTO);

            // Assert
            Hotel savedHotel = hotelRepo.findByName("Haven Plaza"); // Assuming `findByName` is implemented in `HotelRepo`
            assertEquals("Haven Plaza", result);
            assertEquals("123 Street", savedHotel.getAddress());
            assertEquals(200, savedHotel.getPrice());
      }

      @Test
      void testGetHotels() {
            // Arrange
            Hotel hotel1 = new Hotel("Hotel1", 4.2F, "Address1", "Features1", "City1", "Photo1", "Mobile1", "Location1", 150);
            Hotel hotel2 = new Hotel("Hotel2", 3.8F, "Address2", "Features2", "City2", "Photo2", "Mobile2", "Location2", 120);
            hotelRepo.save(hotel1);
            hotelRepo.save(hotel2);

            // Act
            List<Hotel> result = hotelService.getHotels();

            // Assert
            assertEquals(2, result.size());
      }

      @Test
      void testGetOnId() {
            // Arrange
            Hotel hotel = new Hotel("Hotel1", 4.2F, "Address1", "Features1", "City1", "Photo1", "Mobile1", "Location1", 150);
            Hotel savedHotel = hotelRepo.save(hotel);

            // Act
            Hotel result = hotelService.getOnId(savedHotel.getId());

            // Assert
            assertEquals("Hotel1", result.getName());
            assertEquals(150, result.getPrice());
      }

      @Test
      void testGetOnCity() {
            // Arrange
            Hotel hotel = new Hotel("Hotel1", 4.2F, "Address1", "Features1", "NYC", "Photo1", "Mobile1", "Location1", 150);
            hotelRepo.save(hotel);

            // Act
            List<Hotel> result = hotelService.getOnCity("NYC");

            // Assert
            assertEquals(1, result.size());
            assertEquals("NYC", result.get(0).getCity());
      }

      @Test
      void testDelete() {
            // Arrange
            Hotel hotel = new Hotel("Hotel1", 4.2F, "Address1", "Features1", "City1", "Photo1", "Mobile1", "Location1", 150);
            Hotel savedHotel = hotelRepo.save(hotel);

            // Act
            String result = hotelService.delete(savedHotel.getId());

            // Assert
            assertEquals("success", result);
            assertNull(hotelRepo.findById(savedHotel.getId()));
      }
}
