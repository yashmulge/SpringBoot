package com.example.springboot.service;

import java.util.List;
import java.util.Optional;

import com.example.springboot.model.Product;

public interface IProductService {
	List<Product> getProducts();

	Optional<Product> getProductById(String id);

	Product addProduct(Product product);

	Product updateProduct(String id, Product updatedProduct);

	boolean deleteProduct(String id);
}
