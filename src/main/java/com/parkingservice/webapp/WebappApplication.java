package com.parkingservice.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.parkingservice.webapp.repository.UserRepository;

@SpringBootApplication
///@EnableJpaRepositories(basePackages = "com.parkingservice.webapp.repository")
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}
