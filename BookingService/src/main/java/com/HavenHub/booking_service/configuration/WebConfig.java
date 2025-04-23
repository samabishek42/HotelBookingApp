package com.HavenHub.booking_service.configuration;

import com.HavenHub.booking_service.service.DateStringToLocalDateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
      @Override
      public void addFormatters(FormatterRegistry registry) {
            // Register the custom converter for LocalDate
            registry.addConverter(new DateStringToLocalDateConverter());
      }
}