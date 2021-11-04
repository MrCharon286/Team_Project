package com.example.plantforu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

<<<<<<< HEAD
@EnableJpaAuditing
@SpringBootApplication
=======
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
>>>>>>> 박순규-edit
public class TeamProApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamProApplication.class, args);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	
}
