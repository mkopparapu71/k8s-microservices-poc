package com.mypoc.ws.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductLauncher {
	public static void main(String[] args) {
		SpringApplication.run(ProductController.class, args);
	}
}
