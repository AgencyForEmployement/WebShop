package com.webShop.WebShop;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebShopApplication {
	final static Logger log = Logger.getLogger(WebShopApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
		log.info("Hello from WebShopApplication! Welcome back!");
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
