package com.imdb.result;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResultApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResultApplication.class, args);
	}

}
