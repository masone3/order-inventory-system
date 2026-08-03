package com.eastonmason.orderinventory.productservice.service;

import com.eastonmason.orderinventory.productservice.dto.ProductRequest;
import com.eastonmason.orderinventory.productservice.dto.ProductResponse;
import com.eastonmason.orderinventory.productservice.model.Product;
import com.eastonmason.orderinventory.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import com.eastonmason.orderinventory.productservice.exception.ProductNotFoundException;
import com.eastonmason.orderinventory.productservice.exception.InsufficientStockException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductResponse.from(product);
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.sku(),
                request.stockQuantity(),
                request.price()
        );
        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }

    public ProductResponse reserveStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(id, quantity, product.getStockQuantity());
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }
}