package com.example.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springboot.model.Product;

@Service
public class ProductServiceImpl implements IProductService {

	private final List<Product> products = new ArrayList<>();

	@Override
	public List<Product> getProducts() {
		return new ArrayList<>(products);
	}

	@Override
	public Optional<Product> getProductById(String id) {
		return products.stream().filter(product -> product.getId().equals(id)).findFirst();
	}

	@Override
	public Product addProduct(Product product) {
		products.add(product);
		return product;
	}

	@Override
	public Product updateProduct(String id, Product updatedProduct) {
		Optional<Product> productOptional = getProductById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setName(updatedProduct.getName());
			product.setPrice(updatedProduct.getPrice());
			// Add other fields as necessary
			return product;
		}
		return null;
	}

	@Override
	public boolean deleteProduct(String id) {
		return products.removeIf(product -> product.getId().equals(id));
	}
}
