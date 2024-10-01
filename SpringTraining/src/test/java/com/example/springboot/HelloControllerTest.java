package com.example.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springboot.controller.HelloController;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGreetings() throws Exception {
		mockMvc.perform(get("/api/greetings")) // Use the correct endpoint
				.andExpect(status().isOk()).andExpect(content().string("Hello, World!")); // Default value for 'name'
	}

	@Test
	public void testGreetingsWithName() throws Exception {
		mockMvc.perform(get("/api/greetings").param("name", "Alice")) // Provide a name parameter
				.andExpect(status().isOk()).andExpect(content().string("Hello, Alice!")); // Expected response with the
																							// provided name
	}
}
