package com.HavenHub.api_gateway.configuration;

import com.HavenHub.api_gateway.Feign.UserInterface;
import com.HavenHub.api_gateway.entity.HotelUser;
import com.HavenHub.api_gateway.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Import({org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration.class})
public class SecurityConfig {

      @Autowired
      UserDetailsService userDetailsService;

      @Autowired
      JWTFilter jwtFilter;

      @Autowired
      JWTService jwtService;

      @Autowired
      UserInterface ur;

      @Bean
      public HttpFirewall allowSemicolonHttpFirewall() {
            StrictHttpFirewall firewall = new StrictHttpFirewall();
            firewall.setAllowSemicolon(true); // Allow semicolons in the URL
            return firewall;
      }


      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .authorizeRequests(request -> request
                            .requestMatchers("/api/v1/user/registerUser", "/api/v1/user/login", "/oauth2/**").permitAll()
                            .anyRequest().authenticated())
                    .oauth2Login(oauth2 -> oauth2
                            .successHandler((request, response, authentication) -> {
                                  // Cast authentication to OAuth2AuthenticationToken to extract details
                                  OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                                  Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

                                  // Extract user details
                                  String email = (String) attributes.get("email");
                                  String name = (String) attributes.get("name");
                                  String pictureUrl = (String) attributes.get("picture");
                                  String token = jwtService.generateToken(name);

                                  // Prepare the response map
                                  Map<String, Object> responseBody = new HashMap<>();
                                  responseBody.put("token", token);
                                  responseBody.put("role", "OAuth");
                                  responseBody.put("name", name);
                                  responseBody.put("email", email);
                                  responseBody.put("photo", pictureUrl);
                                  HotelUser user = ur.getByEmail(email).getBody();
                                  if (user != null) {
                                        responseBody.put("role", "user");
                                        responseBody.put("userId", String.valueOf(user.getId()));
                                        responseBody.put("token", jwtService.generateToken(user.getName()));
                                        responseBody.put("name", user.getName());
                                  }
                                  // Write the map as JSON response
                                  response.setContentType("application/json");
                                  response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
                                  response.setStatus(HttpServletResponse.SC_OK);
                            })
                            .failureHandler((request, response, exception) -> {
                                  response.setContentType("application/json");
                                  response.getWriter().write("{\"error\": \"Login failed!\"}");
                                  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            }))
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }

      @Bean
      public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(new BCryptPasswordEncoder());
            provider.setUserDetailsService(userDetailsService);
            return provider;
      }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
      }
}
