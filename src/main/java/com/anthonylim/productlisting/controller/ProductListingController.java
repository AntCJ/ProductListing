package com.anthonylim.productlisting.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anthonylim.productlisting.dto.ProductDTO;
import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;
import com.anthonylim.productlisting.service.ProductListingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ProductListingController {
    private ProductListingService productListingService;
    public final static int DEFAULT_PRODUCT_LIST_PAGE_SIZE = 10;
    public final static int MAX_PRODUCT_LIST_PAGE_SIZE = 25;
    
    public ProductListingController(ProductListingService productListingService) {
        this.productListingService = productListingService;
    }
    
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        String logPrefix = this.getClass().getSimpleName() + " createProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            productDTO.validate();
            log.info(logPrefix + "Inputs validated");
            
            return ResponseEntity.ok()
                    .body(ProductDTO.toDTO(
                            productListingService.createProduct(ProductDTO.toEntity(productDTO))
                          )
                    );
        } catch (ProductListingException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductListingException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error", 
                    e
            );
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    @PutMapping("/products/{code}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("code") String code,
            @RequestBody ProductDTO productDTO) {
        String logPrefix = this.getClass().getSimpleName() + " updateProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            if (code == null || code.trim().equals("")) {
                throw new ProductListingException(HttpStatus.BAD_REQUEST,
                        "Code cannot be null/empty",
                        null);
            }
            productDTO.validate();
            
            if (!code.equals(productDTO.getCode())) {
                throw new ProductListingException(HttpStatus.BAD_REQUEST,
                        "Mismatched product code",
                        null);
            }
            log.info(logPrefix + "Inputs validated");
            
            return ResponseEntity.ok()
                    .body(ProductDTO.toDTO(
                            productListingService.updateProduct(code, ProductDTO.toEntity(productDTO))
                          )
                    );
        } catch (ProductListingException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductListingException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error", 
                    e
            );
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    @GetMapping("/products/{code}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("code") String code) {
        String logPrefix = this.getClass().getSimpleName() + " getProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            if (code == null || code.trim().equals("")) {
                throw new ProductListingException(HttpStatus.BAD_REQUEST,
                        "Code cannot be null/empty",
                        null);
            }
            log.info(logPrefix + "Inputs validated");
            
            return ResponseEntity.ok()
                    .body(ProductDTO.toDTO(
                            productListingService.getProduct(code)
                          )
                    );
        } catch (ProductListingException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductListingException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error", 
                    e
            );
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> listProducts(@RequestParam("page") String page,
            @RequestParam("size") String size) {
        String logPrefix = this.getClass().getSimpleName() + " listProducts() : ";
        try {
            log.info(logPrefix + "Enter");
            int _page = 0;
            if (page != null && !page.trim().equals("")) {
                try {
                    _page = Integer.parseInt(page);
                } catch (Exception e) {
                    throw new ProductListingException(HttpStatus.BAD_REQUEST,
                            "Page must be an integer",
                            null);
                }
            }
            
            int _size = DEFAULT_PRODUCT_LIST_PAGE_SIZE;
            if (size != null && !size.trim().equals("")) {
                try {
                    _size = Integer.parseInt(size);
                } catch (Exception e) {
                    throw new ProductListingException(HttpStatus.BAD_REQUEST,
                            "Size must be an integer",
                            null);
                }
            }
            
            if (_size > MAX_PRODUCT_LIST_PAGE_SIZE) {
                throw new ProductListingException(HttpStatus.BAD_REQUEST,
                        "Size cannot be higher than " + MAX_PRODUCT_LIST_PAGE_SIZE,
                        null);
            }
            log.info(logPrefix + "Inputs validated");
            
            Page<Product> products = productListingService.listProducts(_page, _size);
            Page<ProductDTO> productDTOs = new PageImpl<>(
                    products.stream().map(
                            ProductDTO::toDTO
                    ).toList(),
                    products.getPageable(),
                    products.getTotalElements()
            );
            
            return ResponseEntity.ok()
                    .body(productDTOs);
        } catch (ProductListingException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductListingException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error", 
                    e
            );
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
    
    @DeleteMapping("/products/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("code") String code) {
        String logPrefix = this.getClass().getSimpleName() + " deleteProduct() : ";
        try {
            log.info(logPrefix + "Enter");
            if (code == null || code.trim().equals("")) {
                throw new ProductListingException(HttpStatus.BAD_REQUEST,
                        "Code cannot be null/empty",
                        null);
            }
            log.info(logPrefix + "Inputs validated");
            
            productListingService.deleteProduct(code);
            
            return ResponseEntity.ok().body(null);
        } catch (ProductListingException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductListingException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error", 
                    e
            );
        } finally {
            log.info(logPrefix + "Exit");
        }
    }
}
