package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.HotelInterface;
import com.HavenHub.api_gateway.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
      public ResponseEntity<List<Hotel>> getAllHotels() {
            return hs.getAllHotels();
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
