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
    // @Value("${PRODUCTS_SERVICE_HOST}")
    private String productsHost = "products.ecommerce.ecos.fnma";
     
    // @Value("${PRODUCTS_SERVICE_PORT}")
    private String productsPort = "80";
    
    // @Value("${ORDERS_SERVICE_HOST}")
    private String ordersHost = "orders.ecommerce.ecos.fnma";
     
    // @Value("${ORDERS_SERVICE_PORT}")
    private String ordersPort = "80";
    
	@GetMapping("/")
	@ResponseBody
	ArrayList<String> getDefault() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello World!!!");
		list.add("OrdersHost: " + ordersHost);
		list.add("OrdersPort: " + ordersPort);
		list.add("ProductsHost: " + productsHost);
		list.add("ProductsPort: " + productsHost);
		return list;
	}
	
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
