package com.HavenHub.hotel_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
//
//      @Bean
//      public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//            RedisTemplate<String, Object> template = new RedisTemplate<>();
//            template.setConnectionFactory(connectionFactory);
//
//            // Use Jackson serializer for both reading and writing
//            Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//            template.setDefaultSerializer(jacksonSerializer);
//
//            return template;
//      }

}