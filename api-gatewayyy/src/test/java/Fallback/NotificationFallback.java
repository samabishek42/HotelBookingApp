package Fallback;

import com.HavenHub.api_gateway.Feign.NotificationInterface;
import org.springframework.stereotype.Component;
import com.HavenHub.api_gateway.entity.HotelUser;

@Component
public class NotificationFallback implements NotificationInterface {

      @Override
      public void save(HotelUser user) {
            // Log the failure or send a default response
            System.out.println("Notification service is down. Fallback triggered. User not registered for notifications.");

            // Optionally, you can save the user in the local database for retry later, or queue it.
            // Example: logging the failure to a database, file, or monitoring system.

            // A simple return, since save() is a void method, the focus is on logging or handling.
      }
}


