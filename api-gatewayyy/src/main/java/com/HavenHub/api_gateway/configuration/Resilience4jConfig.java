package com.HavenHub.api_gateway.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

      @Bean
      public CircuitBreakerRegistry circuitBreakerRegistry() {
            CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                    .failureRateThreshold(50) // Fail if 50% of requests fail
                    .waitDurationInOpenState(Duration.ofSeconds(10)) // Wait 10 seconds before retry
                    .slidingWindowSize(20) // Evaluate 20 requests
                    .build();

            return CircuitBreakerRegistry.of(config);
      }
}
