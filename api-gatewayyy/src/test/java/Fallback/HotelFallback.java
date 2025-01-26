package Fallback;
import com.HavenHub.api_gateway.Feign.HotelInterface;
import com.HavenHub.api_gateway.entity.Hotel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class HotelFallback implements HotelInterface {

      @Override
      public ResponseEntity<String> addHotel(String name, float ratings, String address, String features, String city, String mobile, String location, String photoPath, int price) {
            return ResponseEntity.status(503).body("Hotel service is currently unavailable. Please try again later.");
      }

      @Override
      public ResponseEntity<List<Hotel>> getAllHotels() {
            return ResponseEntity.status(503).body(Collections.emptyList()); // Fallback to empty list
      }

      @Override
      public ResponseEntity<Hotel> getHotelById(int id) {
            return ResponseEntity.status(503).body(null); // Fallback to null
      }

      @Override
      public ResponseEntity<List<Hotel>> getHotelsByCity(String city) {
            return ResponseEntity.status(503).body(Collections.emptyList()); // Fallback to empty list
      }

      @Override
      public ResponseEntity<String> deleteHotel(int id) {
            return ResponseEntity.status(503).body("Hotel service is currently unavailable. Unable to delete hotel.");
      }
}
