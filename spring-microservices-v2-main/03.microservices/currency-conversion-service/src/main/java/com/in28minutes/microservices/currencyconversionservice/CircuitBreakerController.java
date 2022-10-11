package com.in28minutes.microservices.currencyconversionservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

	private Logger logger =
				LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	public String sampleApi() {
		logger.info("Sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://192.168.245.216:8000/currency-exchange/from/USD/to/INR",
					String.class);
		return forEntity.getBody();
	}

	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
