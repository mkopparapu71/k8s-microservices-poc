package com.mypoc.ws.colorteller;

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
public class ColorTellerController {
    
	@GetMapping("/")
	@ResponseBody
	String getDefault() {
		return "The color of choice currently is Blue!!!";
	}
}
