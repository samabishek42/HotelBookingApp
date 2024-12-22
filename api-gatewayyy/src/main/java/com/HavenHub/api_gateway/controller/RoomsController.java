package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.RoomsInterface;
import com.HavenHub.api_gateway.entity.Rooms;
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
@RequestMapping("api/v1/rooms")
public class RoomsController {

      @Autowired
      RoomsInterface rs;

//
//      @PostMapping(path = "/save")
//      public String saveRooms(@RequestBody RoomsDTO rooms){
//            return rs.addRooms(rooms);
//      }

//      @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//      public ResponseEntity<String>  saveRooms(
//              @RequestParam("hotel_id") int hotelId,
//              @RequestParam("room_number") int roomNumber,
//              @RequestParam("room_type") String roomType,
//              @RequestParam("price") int price,
//              @RequestParam("is_available") String isAvailable,
//              @RequestParam("room_photo") MultipartFile roomPhoto) throws IOException {
//                  // Convert MultipartFile to byte[]
//                  byte[] photoBytes = roomPhoto.getBytes();
//
//                  // Create Rooms entity and save
//                  RoomsDTO room = new RoomsDTO(hotelId, roomNumber, roomType, price, isAvailable, photoBytes);
//                  return new ResponseEntity<>(rs.addRooms(room),HttpStatus.OK);
//      }


      @PostMapping("/save")
      public ResponseEntity<String> addRoom(
              @RequestParam("hotel_id") int hotelId,
              @RequestParam("room_number") int roomNumber,
              @RequestParam("room_type") String roomType,
              @RequestParam("price") int price,
              @RequestParam("is_available") String isAvailable,
              @RequestParam("room_photo") MultipartFile roomPhoto) {

            // Save the room photo in the images/rooms folder
            String uploadDir = "src/main/resources/images/rooms/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                  uploadFolder.mkdirs(); // Create directory if it doesn't exist
            }

            String photoFileName = System.currentTimeMillis() + "_" + roomPhoto.getOriginalFilename();
            File photoFile = new File(uploadDir + photoFileName);
            try {
                  roomPhoto.transferTo(photoFile); // Save the file
            } catch (IOException e) {
                  return new ResponseEntity<>("Failed to upload room photo", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Save room details to the database
            String photoPath = "/images/rooms/" + photoFileName; // Relative path to store in the database
            return rs.addRoom(hotelId,roomNumber,roomType,price,isAvailable,photoPath);
      }


      @GetMapping(path="getAllRooms/{hotel_id}")
      public ResponseEntity<List<Rooms>> getAllHotels(@PathVariable("hotel_id") int  hotel_id){
            return rs.getAllRooms(hotel_id);
      }

}