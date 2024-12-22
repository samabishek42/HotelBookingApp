package com.HavenHub.api_gateway.controller;

import com.HavenHub.api_gateway.Feign.UserInterface;
import com.HavenHub.api_gateway.entity.HotelUser;
import com.HavenHub.api_gateway.service.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/user")
public class HotelUserController {

      @Autowired
      UserInterface ur;

      @Autowired
      JWTService jwt;

      @Autowired
      AuthenticationManager authenticationManager;

      @Autowired
      JWTService jwtService;


      @PostMapping("/registerUser")
      public ResponseEntity<String> saveUser(@RequestBody HotelUser user) {
            return ur.registerUser(user);
      }


      @PostMapping("/save")
      public ResponseEntity<String> saveUser(
              @RequestParam("name") String name,
              @RequestParam("email") String email,
              @RequestParam("password") String password,
              @RequestParam("mobile") String mobile,
              @RequestParam("type") String type,
              @RequestParam(value = "photo", required = false) MultipartFile photo) {


            // Save the photo to the images folder
            String photoPath = null;
            if (photo != null) {
                  String uploadDir = "src/main/resources/images/user";
                  File uploadFolder = new File(uploadDir);
                  if (!uploadFolder.exists()) {
                        uploadFolder.mkdirs();
                  }

                  String photoFileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                  File photoFile = new File(uploadDir + photoFileName);
                  try {
                        photo.transferTo(photoFile);
                        photoPath = "/images/user" + photoFileName; // Relative path to store in DB
                  } catch (IOException e) {
                        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
                  }
            }


            // Save the photo to the images folder
            return ur.saveUser(name, email, password, mobile, type, photoPath);
      }


      @PostMapping(path = "/saveAdmin")
      public ResponseEntity<String> saveAdmin(
              @RequestParam("name") String name,
              @RequestParam("email") String email,
              @RequestParam("password") String password,
              @RequestParam("mobile") String mobile,
              @RequestParam("type") String type,
              @RequestParam(value = "photo", required = false) MultipartFile photo) {

            // Save the photo to the images folder
            String photoPath = null;
            if (photo != null) {
                  String uploadDir = "src/main/resources/images/admin";
                  File uploadFolder = new File(uploadDir);
                  if (!uploadFolder.exists()) {
                        uploadFolder.mkdirs();
                  }

                  String photoFileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                  File photoFile = new File(uploadDir + photoFileName);
                  try {
                        photo.transferTo(photoFile);
                        photoPath = "/images/admin" + photoFileName; // Relative path to store in DB
                  } catch (IOException e) {
                        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
                  }
            }
            return ur.saveAdmin(name, email, password, mobile, type, photoPath);


      }

      @PostMapping("/login")
      public ResponseEntity<Map<String, String>> login(@RequestBody HotelUser user, HttpServletResponse httpServletResponse) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));//IT CALLS AUTHENTICATION MANAGER THEN THE
            // AUTHENTICATION MANAGER CALLS THE AUTHENTICATION PROVIDER
            Map<String, String> response = new HashMap<>();
            HotelUser u=null;
            if (authentication.isAuthenticated()) {
                 u = ur.getByEmail(user.getEmail()).getBody();
                  if (u == null) {
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                  }
            }

            response.put("token", jwt.generateToken(u.getName()));
            response.put("role", u.getType());
            response.put("userId", String.valueOf(u.getId()));
            response.put("name", u.getName());
            response.put("photo", u.getPhoto());

            return ResponseEntity.ok(response);
      }


      @GetMapping("/getOne/{id}")
      public ResponseEntity<HotelUser> getOne(@PathVariable("id") int id) {
            return ur.getOne(id);
      }


      @PostMapping("/logout")
      public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
            String token = authHeader.replace("Bearer ", "");

            // Add the token to a blacklist
            jwtService.blacklistToken(token);


            // Optionally, return a logout success message
            return ResponseEntity.ok("Logged out successfully");
      }

}
