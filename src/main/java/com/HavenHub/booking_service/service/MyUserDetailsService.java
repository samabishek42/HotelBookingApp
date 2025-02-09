package com.HavenHub.booking_service.service;

import com.HavenHub.booking_service.DTO.UserPrincipalDTO;
import com.HavenHub.booking_service.Feign.UserInterface;
import com.HavenHub.booking_service.entity.HotelUser;
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

//      @Override
//      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            HotelUser user=ur.findByName(username);
//
//            if(user==null){
//                  System.out.println("User Not Found");
//                  throw new UsernameNotFoundException("user not found");
//            }
//            return new UserPrincipalDTO(user);
//      }
//
//      @Override
//      public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
//            // Check if identifier is an email or username
//            HotelUser user =ur.findByEmail(identifier); // Assume findByEmail already exists
//            if (user == null) {
//                  user = ur.findByName(identifier); // Fallback to username lookup
//            }
//
//            if (user == null) {
//                  throw new UsernameNotFoundException("User not found with identifier: " + identifier);
//            }
//
//            return new org.springframework.security.core.userdetails.User(
//                    user.getName(), user.getPassword(), new ArrayList<>());
//      }

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
