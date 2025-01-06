package com.HavenHub.booking_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulingConfig {

      @Bean
      public ThreadPoolTaskScheduler taskScheduler() {
            ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
            scheduler.setPoolSize(10); // Set the pool size (number of threads)
            scheduler.setThreadNamePrefix("email-task-"); // Set a prefix for the task threads
            return scheduler;
      }
}
