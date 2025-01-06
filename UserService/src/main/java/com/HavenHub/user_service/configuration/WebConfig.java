package com.HavenHub.user_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfig implements WebMvcConfigurer {

      @Override
      public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/v1/**") // Apply to all paths
                    .allowedOrigins("http://localhost:3000") // Allow frontend origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH") // Allowed HTTP methods
                    .allowedHeaders("*"); // Allow all headers
      }


}