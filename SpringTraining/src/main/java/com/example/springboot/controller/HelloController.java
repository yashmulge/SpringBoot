package com.example.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/greetings")
	public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
		LOGGER.info("Received request for greetings API");
		return String.format("Hello, %s!", name);
	}

	@GetMapping("/stop")
	public String stopServer() {
		LOGGER.info("Received request to stop the server");
		return "Server is stopping";
	}

	@GetMapping("/exit")
	public String exitServer() {
		LOGGER.info("Received request to exit the server");
		// Optionally, perform any cleanup or additional actions before shutting down
		System.exit(0); // Exit the application with code 0
		return "Exiting server..."; // This will not be reached due to the immediate shutdown
	}
}
