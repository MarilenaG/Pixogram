package com.pixogram.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@RestController
@EnableDiscoveryClient
@EnableEurekaClient
public class FrontAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontAppApplication.class, args);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return new ResponseEntity("hello there from the front", HttpStatus.OK);
	}

}
