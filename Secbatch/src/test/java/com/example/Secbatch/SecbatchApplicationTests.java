package com.example.Secbatch;

import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecbatchApplicationTests {

	// @Test
	// void contextLoads() {
	// }
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnCreated() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("Geeta", "test@123")
				.getForEntity("/convert", String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	@Test
	void shouldReturnCreatedDept() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("Geeta", "test@123")
				.getForEntity("/convert/cse", String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	@Test
	void shouldReturnFilenotfound() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("Geeta", "test@123")
				.getForEntity("/convert/file", String.class);
				assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
			//assertThat(response.getBody()).isEqualTo("File Not Found");
	}

}
