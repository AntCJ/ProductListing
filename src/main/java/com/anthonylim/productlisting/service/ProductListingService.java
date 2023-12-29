package com.anthonylim.productlisting.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;
import com.anthonylim.productlisting.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductListingService {
    private ProductRepository productRepository;
    
    public ProductListingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product createProduct(Product product) {
        String logPrefix = this.getClass().getSimpleName() + " createProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            if (productRepository.existsByCode(product.getCode())) {
                throw new ProductListingException(
                        HttpStatus.CONFLICT, 
                        "Product with the same code already exists", 
                        null
                );
            }
            
            return productRepository.save(
                    new Product(
                            product.getCode(),
                            product.getName(),
                            product.getCategory(),
                            product.getBrand(),
                            product.getType(),
                            product.getDescription()
                    )
            );
        } catch (Exception e) {
            log.error(logPrefix + "Error in creating product", e);
            throw e;
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    public Product updateProduct(String code, Product product) {
        String logPrefix = this.getClass().getSimpleName() + " updateProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            Optional<Product> productOptional = productRepository.findByCode(code);
            if (productOptional.isEmpty()) {
                throw new ProductListingException(
                        HttpStatus.NOT_FOUND, 
                        "Product not found", 
                        null
                );
            }
            log.info(logPrefix + "Product found, proceed to updating the product");
            return productRepository.save(
                    new Product(
                            productOptional.get().getId(),
                            code,
                            product.getName(),
                            product.getCategory(),
                            product.getBrand(),
                            product.getType(),
                            product.getDescription()
                    )
            );
        } catch (Exception e) {
            log.error(logPrefix + "Error in updating product", e);
            throw e;
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    public Product getProduct(String code) {
        String logPrefix = this.getClass().getSimpleName() + " getProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            Optional<Product> productOptional = productRepository.findByCode(code);
            if (productOptional.isEmpty()) {
                throw new ProductListingException(
                        HttpStatus.NOT_FOUND, 
                        "Product not found", 
                        null
                );
            }
            log.info(logPrefix + "Product found, proceed to returning the product");
            return productOptional.get();
        } catch (Exception e) {
            log.error(logPrefix + "Error in getting product", e);
            throw e;
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    public Page<Product> listProducts(int page, int size) {
        String logPrefix = this.getClass().getSimpleName() + " listProducts() : ";
        try {
            log.info(logPrefix + "Enter");
            return productRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            log.error(logPrefix + "Error in listing products", e);
            throw e;
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    @Transactional
    public void deleteProduct(String code) {
        String logPrefix = this.getClass().getSimpleName() + " deleteProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            if (!productRepository.existsByCode(code)) {
                throw new ProductListingException(
                        HttpStatus.NOT_FOUND, 
                        "Product not found", 
                        null
                );
            }
            log.info(logPrefix + "Product found, proceed to deleting the product");
            productRepository.deleteByCode(code);
            log.info(logPrefix + "Product deleted");
        } catch (Exception e) {
            log.error(logPrefix + "Error in deleting product", e);
            throw e;
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
}
