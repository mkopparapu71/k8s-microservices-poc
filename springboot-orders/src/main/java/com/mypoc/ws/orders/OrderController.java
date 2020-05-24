package com.mypoc.ws.orders;

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
public class OrderController {
	private OrderDAO dao = OrderDAO.getInstance();
	
	@GetMapping("/")
	@ResponseBody
	List<Order> all() {
		return dao.listAll();
	}
	
	@GetMapping("/{id}")
	Order get(@PathVariable Integer id) {
		return dao.get(id);
	}

	@PostMapping("/")
	Order add(@RequestBody Order order) {

	    Order a = dao.add(order.getName(), order.getTotal());
	    return a;
	}

	@PutMapping("/")
	Order replace(@RequestBody Order replace) {

		Order o = dao.get(replace.getId());
	    if(o != null) {
	    	o.setName(replace.getName());
	    	o.setTotal(replace.getTotal());
	    	
	    	dao.update(o);
	    }

	    return o;
	}

	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		dao.delete(id);
	}
}
