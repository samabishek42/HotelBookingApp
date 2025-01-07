# Hotel Booking Application  

A microservices-based hotel booking application providing seamless user experiences for searching hotels, booking reservations, and managing hotel data.  

## Features  
- **JWT Authentication & OAuth2 Integration** for secure login.  
- Role-based access control for users and admins.  
- RESTful APIs for managing users, hotels, rooms, and bookings.  
- Microservices architecture with decoupled services:  
  - **User Service**: Manages user information.  
  - **Hotel Service**: Manages hotel details.  
  - **Room Service**: Manages room details.  
  - **Booking Service**: Handles booking operations.  
  - **Notification Service** for booking confirmations and cancellations remainder asynchronously through emails and SMS.  
- Frontend built using **React.js**.  

---

## Technologies Used  
- Backend: **Spring Boot**, **Java**  
- Frontend: **React.js**  
- Database: **MySQL**  
- Security: **Spring Security**, **OAuth2**, **JWT**  
- Communication: **Feign Clients**, **REST APIs**  

---

## Setup Instructions  

### Prerequisites  
- **Java 17+**  
- **Node.js**  
- **MySQL Server**  
- **Postman** (optional, for API testing)  
- IDE of your choice (e.g., IntelliJ IDEA, Eclipse)  

---

### Steps  

#### 1. Clone the Repository  
git clone https://github.com/your-repo/hotel-booking-app.git  
cd hotel-booking-app  

2. Database Setup
Run the following SQL commands to create databases for each service:

CREATE DATABASE user_service;  
CREATE DATABASE hotel_service;  
CREATE DATABASE room_service;  
CREATE DATABASE booking_service;  

3. Separate Workspaces for Each Service
Create a separate workspace for each service:
User Service
Hotel Service
Room Service
Booking Service
API Gateway
Service Registry
Open your IDE and create individual projects for each service.
Copy the corresponding service folder from the repository into its respective workspace.
4. Service Registry Setup
Navigate to the service-registry folder:
cd service-registry  
Build and run the service registry:
mvn clean install  
java -jar target/service-registry.jar
 
5. User Service Setup
Navigate to the user-service folder:
cd user-service  
Update the application.yml file with your database and service registry configuration.
Build and run the service:
mvn clean install  
java -jar target/user-service.jar

6. Hotel Service Setup
Navigate to the hotel-service folder:
cd hotel-service  
Update the application.yml file with your database and service registry configuration.
Build and run the service:
mvn clean install  
java -jar target/hotel-service.jar

7. Room Service Setup
Navigate to the room-service folder:
cd room-service  
Update the application.yml file with your database and service registry configuration.
Build and run the service:
mvn clean install  
java -jar target/room-service.jar

8. Booking Service Setup
Navigate to the booking-service folder:
cd booking-service  
Update the application.yml file with your database and service registry configuration.
Build and run the service:
mvn clean install  
java -jar target/booking-service.jar

9. API Gateway Setup
Navigate to the api-gateway folder:
cd api-gateway  
Update the application.yml file with routing details for each service.
Build and run the API Gateway:
mvn clean install  
java -jar target/api-gateway.jar

10. Frontend Setup
Navigate to the frontend folder:
cd frontend  
Install dependencies:
npm install  
Start the development server:
npm start

Notes
Ensure the Service Registry is up and running before starting other services.
Each service (User, Hotel, Room, Booking, API Gateway) should run in a separate terminal or IDE instance.
Use Postman or similar tools to test individual APIs before integrating with the frontend.
Service Details
1. User Service
Entity: HotelUser
Endpoints:
/register
/login
2. Hotel Service
Entity: Hotel
Endpoints:
/hotels
/hotels/{id}
3. Room Service
Entity: Room
Endpoints:
/rooms
/rooms/{id}
4. Booking Service
Entity: Booking
Endpoints:
/bookings
/bookings/{id}
Usage
Open the React.js frontend and access the login or register page.
Search for hotels and rooms to book reservations through the user dashboard.
Admins can manage hotels, rooms, and bookings.
Contributing
Contributions are welcome! Please fork this repository and create a pull request for any enhancements or bug fixes.



