package Fallback;
import com.HavenHub.api_gateway.Feign.UserInterface;
import com.HavenHub.api_gateway.entity.HotelUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class UserFallback implements UserInterface {

      @Override
      public HotelUser getByEmail(String email) {
            // Fallback response when unable to fetch user by email
            return new HotelUser(); // Returning an empty user
      }

      @Override
      public ResponseEntity<HotelUser> getByName(String name) {
            return ResponseEntity.status(503).body(new HotelUser()); // Fallback to empty user
      }

      @Override
      public ResponseEntity<String> saveUser(String name, String email, String password, String mobile, String type, String photoPath) {
            return ResponseEntity.status(503).body("User service is unavailable. Unable to save user.");
      }

      @Override
      public ResponseEntity<String> saveAdmin(String name, String email, String password, String mobile, String type, String photoPath) {
            return ResponseEntity.status(503).body("User service is unavailable. Unable to save admin.");
      }

      @Override
      public ResponseEntity<String> registerUser(HotelUser user) {
            return ResponseEntity.status(503).body("User service is unavailable. Unable to register user.");
      }

      @Override
      public ResponseEntity<String> registerAdmin(HotelUser user) {
            return ResponseEntity.status(503).body("User service is unavailable. Unable to register admin.");
      }

      @Override
      public ResponseEntity<Map<String, String>> login(HotelUser user) {
            return ResponseEntity.status(503).body(Map.of("error", "User service unavailable"));
      }

      @Override
      public ResponseEntity<HotelUser> getOne(int id) {
            return ResponseEntity.status(503).body(new HotelUser()); // Fallback to empty user
      }

      @Override
      public ResponseEntity<String> saveOAUth(HotelUser user) {
            return ResponseEntity.status(503).body("User service is unavailable. Unable to save OAuth user.");
      }
}
