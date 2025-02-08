package com.HavenHub.hotel_service.controller;

import com.HavenHub.hotel_service.DTO.HotelDTO;
import com.HavenHub.hotel_service.entity.Hotel;
import com.HavenHub.hotel_service.service.HotelService;
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

      @Autowired
      HotelService hs;


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
                  HotelDTO hotel = new HotelDTO(name, ratings, address, features, city, photoPath, mobile, location,price);
                  hs.addHotel(hotel);

                  return new ResponseEntity<>("Hotel added successfully!", HttpStatus.OK);
            }



      @GetMapping(path="/getAllHotels")
      public ResponseEntity<List<Hotel>> getAllHotels() throws Exception { 
            List<Hotel> list=hs.getHotels();
            if(list.isEmpty())
                  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
           return new ResponseEntity<>(list,HttpStatus.OK);
      }


      @GetMapping(path = "/getOne/{id}")
      public ResponseEntity<Hotel> getHotel(@PathVariable("id") int id){
            Hotel h= hs.getOnId(id);
            if(h==null)
                  return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            return  new ResponseEntity<>(h,HttpStatus.OK);
      }


      @GetMapping("getOneHotel/{city}")
      public ResponseEntity<List<Hotel>> getHotelByCity(@PathVariable("city")String city){
            List<Hotel> list = hs.getOnCity(city);
            if(list.isEmpty())
                  return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
            return  new ResponseEntity<>(list,HttpStatus.OK);
      }


      @DeleteMapping("deleteHotel/{id}")
      public ResponseEntity<String>  deleteHotel(@PathVariable("id") int id){
            return new ResponseEntity<>(hs.delete(id),HttpStatus.OK);
      }


}
