package com.HavenHub.user_service.service;

import com.HavenHub.user_service.DTO.HotelUserDTO;
import com.HavenHub.user_service.entity.HotelUser;
import com.HavenHub.user_service.repository.HotelUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class HotelUserService {

      @Autowired
      HotelUserRepo ur;


      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private JWTService jwt;

      public String addUser(HotelUser user) {
            if(ur.findByEmail(user.getEmail())!=null)
                  return "conflict";
            user.setType("user");
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            ur.save(user);
            return user.getName();
      }

      public String addAdmin(HotelUser user) {
            if(ur.findByEmail(user.getEmail())!=null)
                  return "conflict";

            user.setType("admin");
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            ur.save(user);
            return user.getName();
      }
//
//      public String loginUser(HotelUserDTO user) {
//            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    user.getEmail(),user.getPassword()));//IT CALLS AUTHENTICATION MANAGER THEN THE
//            // AUTHENTICATION MANAGER CALLS THE AUTHENTICATION PROVIDER
//
//            if(authentication.isAuthenticated()){
//                  HotelUser u=ur.findByEmail(user.getEmail());
//                  if (u == null) {
//                        throw new UsernameNotFoundException("User not found with email: " + user.getEmail());
//                  }
//                  return jwt.generateToken(u.getName());
//            }
//
//            return "failure";
//      }

      public HotelUser getUser(int id) {
            return ur.findById(id);
      }

      public HotelUser getByEmail(String email){
            return ur.findByEmail(email);
      }

      public HotelUser getByName(String name){
            return ur.findByName(name);
      }
}
