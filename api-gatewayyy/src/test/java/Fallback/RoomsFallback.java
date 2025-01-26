package Fallback;
import com.HavenHub.api_gateway.Feign.RoomsInterface;
import com.HavenHub.api_gateway.entity.Rooms;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class RoomsFallback implements RoomsInterface {

      @Override
      public ResponseEntity<String> addRoom(int hotelId, int roomNumber, String roomType, int price, String isAvailable, String photoPath) {
            // Log and return an appropriate fallback message
            return ResponseEntity.status(503).body("Rooms service is currently unavailable. Please try again later.");
      }

      @Override
      public ResponseEntity<List<Rooms>> getAllRooms(int hotelId) {
            // Return empty list as a fallback
            return ResponseEntity.status(503).body(Collections.emptyList());
      }
}
