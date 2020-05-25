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
    @Value("${PRODUCTS_SERVICE_HOST}")
    private String productsHost;
     
    @Value("${PRODUCTS_SERVICE_PORT}")
    private String productsPort;
    
    @Value("${ORDERS_SERVICE_HOST}")
    private String ordersHost;
     
    @Value("${ORDERS_SERVICE_PORT}")
    private String ordersPort;
    
	@GetMapping("/orders")
	@ResponseBody
	List<Order> allOrders() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + ordersHost + ":" + ordersPort;
		ArrayList<Order> orders = restTemplate.getForObject(url, ArrayList.class);
		return orders;
	}
	
	@GetMapping("/orders/{id}")
	Order getOrder(@PathVariable Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + ordersHost + ":" + ordersPort + "/" + id;
		Order order = restTemplate.getForObject(url, Order.class);
		return order;
	}

	@GetMapping("/products")
	@ResponseBody
	List<Product> allProducts() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + productsHost + ":" + productsPort;
		ArrayList<Product> products = restTemplate.getForObject(url, ArrayList.class);
		return products;
	}
	
	@GetMapping("/products/{id}")
	Product getProduct(@PathVariable Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + productsHost + ":" + productsPort + "/" + id;
		Product product = restTemplate.getForObject(url, Product.class);
		return product;
	}
}
