package com.mypoc.ws.frontend;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@EnableAutoConfiguration
public class FrontendController {
    @Value("${PRODUCTS_APP_SERVICE_HOST}")
    private String productsAppHost;
     
    @Value("${PRODUCTS_APP_SERVICE_PORT}")
    private String productsAppPort;
    
    @Value("${ORDERS_APP_SERVICE_HOST}")
    private String ordersAppHost;
     
    @Value("${ORDERS_APP_SERVICE_PORT}")
    private String ordersAppPort;
    
	@GetMapping("/orders")
	@ResponseBody
	List<Order> allOrders() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + ordersAppHost + ":" + ordersAppPort;
		ArrayList<Order> orders = restTemplate.getForObject(url, ArrayList.class);
		return orders;
	}
	
	@GetMapping("/orders/{id}")
	Order getOrder(@PathVariable Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + ordersAppHost + ":" + ordersAppPort + "/" + id;
		Order order = restTemplate.getForObject(url, Order.class);
		return order;
	}

	@GetMapping("/products")
	@ResponseBody
	List<Product> allProducts() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + productsAppHost + ":" + productsAppPort;
		ArrayList<Product> products = restTemplate.getForObject(url, ArrayList.class);
		return products;
	}
	
	@GetMapping("/products/{id}")
	Product getProduct(@PathVariable Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + productsAppHost + ":" + productsAppPort + "/" + id;
		Product product = restTemplate.getForObject(url, Product.class);
		return product;
	}
}
