package com.HavenHub.user_service.configuration;


import com.HavenHub.user_service.repository.HotelUserRepo;
import com.HavenHub.user_service.service.HotelUserService;
import com.HavenHub.user_service.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                    .cors(Customizer.withDefaults()) // Enable CORS if required
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll() // Allow all requests
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                    .build();
      }

      @Bean
      public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
      }
//
//      @Bean
//      public AuthenticationProvider authenticationProvider(){
//            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//            provider.setPasswordEncoder(new BCryptPasswordEncoder());
//            provider.setUserDetailsService(userDetailsService);
//            return provider;
//      }
//
//      @Bean
//      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//            return config.getAuthenticationManager();
      //}

}
