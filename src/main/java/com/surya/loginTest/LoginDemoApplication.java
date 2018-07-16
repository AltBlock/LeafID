package com.surya.loginTest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.surya.loginTest.repository.StorageProperties;
import com.surya.loginTest.repository.StorageService;



@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LoginDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginDemoApplication.class, args);
	}
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
	}
	
}
