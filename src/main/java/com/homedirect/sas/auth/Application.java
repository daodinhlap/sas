package com.homedirect.sas.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.homedirect.sas.auth.security.JwtConfig;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}

}
