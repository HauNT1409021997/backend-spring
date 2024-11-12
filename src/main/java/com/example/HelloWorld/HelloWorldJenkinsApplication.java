package com.example.HelloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.example.HelloWorld.model")
public class HelloWorldJenkinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldJenkinsApplication.class, args);
	}

}
