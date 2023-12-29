package com.anthonylim.productlisting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonylim.productlisting.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByCode(String code);
    
    Optional<Product> findByCode(String code);
    
    void deleteByCode(String code);
}
