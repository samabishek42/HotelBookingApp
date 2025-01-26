package Fallback;
import com.HavenHub.api_gateway.Feign.BookingInterface;
import com.HavenHub.api_gateway.entity.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class BookingFallback implements BookingInterface {

      @Override
      public ResponseEntity<String> saveBooking(Booking booking, int user_id) {
            return ResponseEntity.status(503).body("Booking service is currently unavailable. Please try again later.");
      }

      @Override
      public ResponseEntity<List<Booking>> getBookedRoomsByUser(int userId) {
            return ResponseEntity.status(503).body(Collections.emptyList()); // Fallback to empty list
      }

      @Override
      public ResponseEntity<List<Booking>> getAllRooms(int hotelId, LocalDate date) {
            return ResponseEntity.status(503).body(Collections.emptyList()); // Fallback to empty list
      }

      @Override
      public ResponseEntity<String> updateCancelled(int bookingId, int user_id) {
            return ResponseEntity.status(503).body("Booking service is currently unavailable. Unable to cancel booking.");
      }
}
