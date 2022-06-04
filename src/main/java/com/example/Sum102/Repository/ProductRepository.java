package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll(); // 전체 목록 보기

    List<Product> findAll(String title); // 검색 문자열이 있는 목록 보기

    Product save(Product product); // 도서 추가

    Product findOne(Long pid);
}
