package com.HavenHub.rooms_service.service;

import com.HavenHub.rooms_service.DTO.UserPrincipalDTO;
import com.HavenHub.rooms_service.Feign.UserInterface;
import com.HavenHub.rooms_service.entity.HotelUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

      @Autowired
      private UserInterface ur;

      @Autowired
      private HttpSession session;



      @Override
      public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
            HotelUser user;

            // Determine if identifier is an email or username
            if (identifier.contains("@")) { // Assume email if '@' is present
                  user = ur.getByEmail(identifier).getBody();
            } else {
                  user = ur.getByName(identifier).getBody();
            }

            if (user == null) {
                  System.out.println("User Not Found");
                  throw new UsernameNotFoundException("User not found with identifier: " + identifier);
            }
           session.setAttribute("role",user.getType());

            return new UserPrincipalDTO(user); // Use UserPrincipalDTO to return user details
      }
}
