package com.HavenHub.booking_service.configuration;

import com.HavenHub.booking_service.service.JWTService;
import com.HavenHub.booking_service.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JWTFilter extends OncePerRequestFilter {

      @Autowired
      JWTService jwtService;

      @Autowired
      ApplicationContext context;

      @Autowired
      MyUserDetailsService myUserDetailsService;

//      @Override
//      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//            String authHeader=request.getHeader("Authorization");
//            String token=null;
//            String username=null;
//
//            if(authHeader!=null&&authHeader.startsWith("Bearer ")){
//                  token=authHeader.substring(7);
//                  username=jwtService.extractUserName(token);
//            }
//            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//                  UserDetails userDetails=context.getBean(MyUserDetailsService.class).
//                          loadUserByUsername(username);
//                  if(jwtService.validateTokens(token,userDetails)){
//                        UsernamePasswordAuthenticationToken authenticationToken=
//                                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                  }
//            }
//            filterChain.doFilter(request,response);
//      }

      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
              throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            // Validate the Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                  filterChain.doFilter(request, response);
                  return;
            }

            // Extract the token
            token = authHeader.substring(7);

            try {
                  // Extract username from token
                  username = jwtService.extractUserName(token);

                  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        // Load user details
                        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                        // Validate the token
                        if (jwtService.validateTokens(token, userDetails)) {
                              UsernamePasswordAuthenticationToken authenticationToken =
                                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        } else {
                              logger.warn("Invalid JWT token for user: " + username);
                        }
                  }
            } catch (Exception ex) {
                  logger.error("Error during JWT validation: ", ex);
                  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                  response.getWriter().write("Unauthorized");
                  return;
            }

            filterChain.doFilter(request, response);
      }

}

