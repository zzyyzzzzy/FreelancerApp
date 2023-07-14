package com.mercury.FreelancerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FreelancerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelancerAppApplication.class, args);
	}

}
