package com.HavenHub.rooms_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RoomsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomsServiceApplication.class, args);
	}

}
