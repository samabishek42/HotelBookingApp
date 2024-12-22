package com.HavenHub.api_gateway.service;


import com.HavenHub.api_gateway.DTO.UserPrincipalDTO;
import com.HavenHub.api_gateway.Feign.UserInterface;
import com.HavenHub.api_gateway.entity.HotelUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

      @Autowired
      private UserInterface ur;

      protected final Log logger = LogFactory.getLog(getClass());

      @Override
      public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
            HotelUser user;

            // Determine if identifier is an email or username
            if (identifier.contains("@")) { // Assume email if '@' is present
                  logger.info("Email method called");
                  user = ur.getByEmail(identifier).getBody();
            } else {
                  logger.info("Name method called");
                  user = ur.getByName(identifier).getBody();
            }

            if (user == null) {
                  logger.error("User Not found");
                  throw new UsernameNotFoundException("User not found with identifier: " + identifier);
            }


            return new UserPrincipalDTO(user); // Use UserPrincipalDTO to return user details
      }
}
