package com.example.Sum102.Service;

import com.example.Sum102.Domain.Product;
import com.example.Sum102.Repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    // 도서 추가
    public Long addProduct(Product product){
        productRepository.save(product);
        return product.getId();

    }
    public Product findProduct(Long pid){
        return productRepository.findOne(pid);
    }
}
