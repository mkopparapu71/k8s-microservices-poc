package com.mypoc.ws.products;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ProductController {
	private ProductDAO dao = ProductDAO.getInstance();
	
	@GetMapping("/")
	@ResponseBody
	List<Product> all() {
		return dao.listAll();
	}
	
	@GetMapping("/{id}")
	Product get(@PathVariable Integer id) {
		return dao.get(id);
	}

	@PostMapping("/")
	Product add(@RequestBody Product prod) {

	    Product p = dao.add(prod.getName(), prod.getPrice());
	    return p;
	}

	@PutMapping("/")
	Product replace(@RequestBody Product replace) {

	    Product p = dao.get(replace.getId());
	    if(p != null) {
	    	p.setName(replace.getName());
	    	p.setPrice(replace.getPrice());
	    	
	    	dao.update(p);
	    }

	    return p;
	}

	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		dao.delete(id);
	}
}
