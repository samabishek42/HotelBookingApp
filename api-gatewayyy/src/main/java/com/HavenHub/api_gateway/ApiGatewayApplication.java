package com.HavenHub.api_gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//package com.HavenHub.api_gateway;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//
//@SpringBootApplication(
//	exclude = {
//		DataSourceAutoConfiguration.class,
//		ReactiveOAuth2ClientAutoConfiguration.class
//	}
//)
@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
public class ApiGatewayApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
