package com.HavenHub.user_service.controller;

import com.HavenHub.user_service.entity.HotelUser;
import com.HavenHub.user_service.repository.HotelUserRepo;
import com.HavenHub.user_service.Feign.NotificationRepo;
import com.HavenHub.user_service.service.HotelUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/user")
public class HotelUserController {

      @Autowired
      HotelUserRepo ur;

      @Autowired
      HotelUserService us;

      @Autowired
      HttpSession session;
      // Assuming you're using an email validation service API like ZeroBounce, etc.


      @Autowired
      private NotificationRepo notificationRepo;

      // Twilio credentials from your dashboard
      public static final String ACCOUNT_SID = "AC340bbd34c29132b914e8f013f3037da4"; // Replace with your Account SID
      public static final String AUTH_TOKEN = "49f45c04a9b233194136b4f10c8192ea";   // Replace with your Auth Token


      private static final Logger logger = LoggerFactory.getLogger(HotelUserController.class);

      @PostMapping("/registerUser")
      public ResponseEntity<String> saveUser(@RequestBody HotelUser user) {

            // Check if the email exists in DB
            HotelUser existingUser = ur.findByEmail(user.getEmail());
            if (existingUser != null) {
                  logger.error("Email exists");
                  return new ResponseEntity<>("Email already exists in DB", HttpStatus.CONFLICT);
            }

            // Log the user object before sending to Notification Service
            logger.info("User being sent to Notification Service: {}", user);
            String s=us.addUser(user);

            try {
                  notificationRepo.save(ur.findByEmail(user.getEmail()));
            } catch (Exception e) {
                  logger.error(e.getMessage());
            }
            return new ResponseEntity<> (s, HttpStatus.OK);
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

            // Create user entity
            HotelUser user = new HotelUser(name, email, password, mobile, type, photoPath);

            // Save the user
            if (us.addUser(user).equals("conflict"))
                  return new ResponseEntity<>("Email already exists in DB", HttpStatus.CONFLICT);

            return new ResponseEntity<>(us.addUser(user), HttpStatus.OK);

      }


      @PostMapping("/registerAdmin")
      public ResponseEntity<String> saveAdmin(@RequestBody HotelUser user){
            if (us.addAdmin(user).equals("conflict"))
                  return new ResponseEntity<>("Email already exists in DB", HttpStatus.CONFLICT);

            return new ResponseEntity<>(us.addUser(user), HttpStatus.OK);
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

            // Create user entity
            HotelUser user = new HotelUser(name, email, password, mobile, type, photoPath);

            if (us.addAdmin(user).equals("conflict"))
                  return new ResponseEntity<>("Email already exists in DB", HttpStatus.CONFLICT);

            return new ResponseEntity<>(us.addAdmin(user), HttpStatus.OK);
      }

//      @PostMapping("/login")
//      public ResponseEntity<Map<String, String>> login(@RequestBody HotelUserDTO user, HttpServletResponse httpServletResponse) {
//            String token = us.loginUser(user);
//            // 200 OK status for successful login
//            String role = (String) session.getAttribute("role");
//            HotelUser u = ur.findByEmail(user.getEmail());
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            response.put("role", role);
//            response.put("userId", String.valueOf(u.getId()));
//            response.put("name", u.getName());
//            response.put("photo", u.getPhoto());
//            if (Objects.equals(token, "failure"))
//                  return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//
//            return ResponseEntity.ok(response);
//      }

      @GetMapping("/getOne/{id}")
      public ResponseEntity<HotelUser> getOne(@PathVariable("id") int id) {
            HotelUser h = us.getUser(id);
            if (h == null) {
                  // new ResponseEntity<>(null, new HttpStatus(404));//IT IS ALSO SAME LIKE BELOW ONE
                  System.out.println("Login failed");
                  return new ResponseEntity<>(null, HttpStatus.valueOf(404));//NOT FOUND
            }
            System.out.println("Login successful");
            return new ResponseEntity<>(h, HttpStatus.OK);
      }

      @GetMapping("/getByEmail/{email}")
      public ResponseEntity<HotelUser> getByEmail(@PathVariable("email") String email){
            HotelUser h = us.getByEmail(email);
            if (h == null) {
                  // new ResponseEntity<>(null, new HttpStatus(404));//IT IS ALSO SAME LIKE BELOW ONE
                  System.out.println("No such user is present");
                  return new ResponseEntity<>(null, HttpStatus.valueOf(404));//NOT FOUND
            }
            System.out.println(h);
            return new ResponseEntity<>(h, HttpStatus.OK);
      }

      @GetMapping("/getByName/{name}")
      public ResponseEntity<HotelUser> getByName(@PathVariable("name") String name){
            HotelUser h = us.getByName(name);
            if (h == null) {
                  // new ResponseEntity<>(null, new HttpStatus(404));//IT IS ALSO SAME LIKE BELOW ONE
                  System.out.println("No such user is present");
                  return new ResponseEntity<>(null, HttpStatus.valueOf(404));//NOT FOUND
            }
            System.out.println(h);
            return new ResponseEntity<>(h, HttpStatus.OK);
      }

}
