package com.example.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.model.Product;
import com.example.springboot.service.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private final IProductService productService;

	public ProductController(IProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public List<Product> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductById(@PathVariable String id) {
		Optional<Product> product = productService.getProductById(id);
		return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product addedProduct = productService.addProduct(product);
		return ResponseEntity.ok().body(addedProduct);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(id, product);
		if (updatedProduct != null) {
			return ResponseEntity.ok().body(updatedProduct);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable String id) {
		boolean deleted = productService.deleteProduct(id);
		if (deleted) {
			return ResponseEntity.ok().body("Product with ID " + id + " deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
		}
	}
}
