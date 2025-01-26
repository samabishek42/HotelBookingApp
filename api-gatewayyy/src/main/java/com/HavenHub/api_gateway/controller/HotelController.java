package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.HotelInterface;
import com.HavenHub.api_gateway.entity.Hotel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/hotel")
public class HotelController {


//      @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//      public ResponseEntity<String>  saveHotel(
//              @RequestParam("name") String name,
//              @RequestParam("ratings") float ratings,
//              @RequestParam("address") String address,
//              @RequestParam("features") String features,
//              @RequestParam("city") String city,
//              @RequestParam("mobile") String mobile,
//              @RequestParam("hotel_photo") MultipartFile hotelPhoto) throws IOException {
//                  // Convert MultipartFile to byte[]
//                  byte[] photoBytes = hotelPhoto.getBytes();
//
//                  // Create HotelDTO manually
//                  HotelDTO hotel = new HotelDTO();
//                  hotel.setName(name);
//                  hotel.setRatings(ratings);
//                  hotel.setAddress(address);
//                  hotel.setFeatures(features);
//                  hotel.setCity(city);
//                  hotel.setMobile(mobile);
//                  hotel.setHotel_photo(photoBytes);
//
//                  // Save using the service layer
//                  return new ResponseEntity<>(hs.addHotel(hotel),HttpStatus.OK);
//

      @Autowired
      HotelInterface hs;

      @PostMapping("/save")
      public ResponseEntity<String> addHotel(
              @RequestParam("name") String name,
              @RequestParam("ratings") float ratings,
              @RequestParam("address") String address,
              @RequestParam("features") String features,
              @RequestParam("city") String city,
              @RequestParam("mobile") String mobile,
              @RequestParam("location") String location,
              @RequestParam("photo") MultipartFile photo,
              @RequestParam("price") int price) {

            // Save the photo to the images folder
            String uploadDir = "src/main/resources/images/hotel";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                  uploadFolder.mkdirs(); // Create directory if it doesn't exist
            }

            String photoFileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            File photoFile = new File(uploadDir + photoFileName);
            try {
                  photo.transferTo(photoFile); // Save the file
            } catch (IOException e) {
                  return new ResponseEntity<>("Failed to upload photo", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Save hotel details to the database
            String photoPath = "/images/hotel" + photoFileName; // Relative path to store in the database
            return hs.addHotel(name, ratings, address, features, city, mobile, location, photoPath, price);
      }

      @GetMapping(path = "/getAllHotels")
      @CircuitBreaker(name = "default", fallbackMethod = "getAllHotelsFallback")
      @TimeLimiter(name = "default")
      public CompletableFuture<ResponseEntity<List<Hotel>>> getAllHotels() throws Exception {
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> hs.getAllHotels())
                    .orTimeout(6, TimeUnit.SECONDS) // Cancel task after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());

                          throw new RuntimeException("Timeout occurred", ex);

                    });
      }

      // Fallback Method
      public CompletableFuture<ResponseEntity<List<Hotel>>> getAllHotelsFallback(Exception e) {
            // Asynchronously call the service and apply a timeout
            return CompletableFuture.supplyAsync(() -> hs.getAllHotels())
                    .orTimeout(6, TimeUnit.SECONDS) // Cancel task after 6 seconds
                    .exceptionally(ex -> {
                          // Log timeout exception
                          System.err.println("Timeout occurred. Returning fallback response. Error: " + ex.getMessage());

                          // Return fallback response immediately
                          return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                  .body(Collections.emptyList());
                    });
      }

      @GetMapping(path = "/getOne/{id}")
      public ResponseEntity<Hotel> getHotel(@PathVariable("id") int id) {
            return getHotel(id);
      }


      @GetMapping("getOneHotel/{city}")
      public ResponseEntity<List<Hotel>> getHotelByCity(@PathVariable("city") String city) {
            return getHotelByCity(city);
      }


      @DeleteMapping("deleteHotel/{id}")
      public ResponseEntity<String> deleteHotel(@PathVariable("id") int id) {
            return deleteHotel(id);
      }



}










//      @GetMapping(path = "/getAllHotels")
//      @CircuitBreaker(name = "getAllHotelsService", fallbackMethod = "getAllHotelsFallback")
//      public ResponseEntity<List<Hotel>> getAllHotels() {
//
//                  // Applying the timeout directly on the method
//                  Callable<ResponseEntity<List<Hotel>>> callable = () -> hs.getAllHotels();
//                  Timeout timeout = Timeout.of(Duration.ofSeconds(10)); // Timeout of 10 seconds
//                  return timeout.decorateCallable(callable).call();
//
//
//      }
//
//
//      // Fallback method
//      public ResponseEntity<List<Hotel>> getAllHotelsFallback(Exception e) {
//            // Log the failure
//            System.err.println("Primary attempt to fetch all hotels failed. Retrying in fallback. Error: " + e.getMessage());
//
//            try {
//                  // Retry the service call once
//                  return hs.getAllHotels();
//            } catch (Exception retryException) {
//                  // Log retry failure
//                  System.err.println("Fallback retry to fetch all hotels failed. Error: " + retryException.getMessage());
//
//                  // Return a meaningful response to the client
//                  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                          .body(Collections.emptyList()); // Return an empty list if service is unavailable
//            }
//      }

//
//      @GetMapping(path = "/getAllHotels")
//      @CircuitBreaker(name = "default", fallbackMethod = "getAllHotelsFallback")
//      @TimeLimiter(name = "default")
//      public CompletableFuture<ResponseEntity<List<Hotel>>> getAllHotels() throws Exception{
//            return CompletableFuture.supplyAsync(() -> hs.getAllHotels());
//      }
//
//
//      // Default Fallback Method
//      public CompletableFuture<ResponseEntity<List<Hotel>>> getAllHotelsFallback(Exception e) {
//            // Retry the service call once before returning the fallback response
//            try {
//                  // Retry the original call
//                  return CompletableFuture.supplyAsync(() -> hs.getAllHotels());
//            } catch (Exception retryException) {
//                  // Log retry failure
//                  System.err.println("Retry failed. Returning fallback. Error: " + retryException.getMessage());
//
//                  // Return a meaningful default response after retry failure
//                  return CompletableFuture.completedFuture(
//                          ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                                  .body(Collections.emptyList()) // Return empty list for fallback cases
//                  );
//            }
//      }

//      @GetMapping(path = "/getAllHotels")
//      @CircuitBreaker(name = "default", fallbackMethod = "getAllHotelsFallback")
//      @TimeLimiter(name = "default")
//      public ResponseEntity<List<Hotel>> getAllHotels() throws Exception{
//            return hs.getAllHotels();
//      }
//
//
//      // Default Fallback Method
//      public ResponseEntity<List<Hotel>> getAllHotelsFallback(Exception e) {
//            // Retry the service call once before returning the fallback response
//            try {
//                  // Retry the original call
//                  return hs.getAllHotels();
//            } catch (Exception retryException) {
//                  // Log retry failure
//                  System.err.println("Retry failed. Returning fallback. Error: " + retryException.getMessage());
//
//                  // Return a meaningful default response after retry failure
//                  return
//                          ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                                  .body(Collections.emptyList());// Return empty list for fallback cases
//            }
//      }
