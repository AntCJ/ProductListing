package com.anthonylim.productlisting.service;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import com.anthonylim.productlisting.dto.ProductDTO;
import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;
import com.anthonylim.productlisting.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductListingServiceTest {
    private ProductListingService productListingService;
    
    int id = 11;
    String code = "P011";
    String name = "REALME 5 PRO 6+ 128GB";
    String category = "Mobile & Gadgets";
    String brand = "Realme";
    String type = "Mobile Phones";
    String description = "REALME 5 PRO 6+ 128GB";
    ProductDTO productDTO = new ProductDTO(id, code, name, category, brand, type, description);
    Product product = new Product(id, code, name, category, brand, type, description);
    
    int page = 0;
    int size = 5;
    int totalSize = 1;
    
    @Mock
    private ProductRepository productRepository;
    
    @BeforeEach
    public void beforeEach() {
        productListingService = new ProductListingService(productRepository);
    }
    
    @Test
    public void testCreateProduct() {
        Mockito.when(productRepository.existsByCode(Mockito.eq(code))).thenReturn(false);
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        Product response = productListingService.createProduct(product);
        Assertions.assertEquals(id, response.getId());
    }
    
    @Test
    public void testCreateProduct_throwConflictError() {
        Mockito.when(productRepository.existsByCode(Mockito.eq(code))).thenReturn(true);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingService.createProduct(product));
        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
    
    @Test
    public void testUpdateProduct() {
        Mockito.when(productRepository.findByCode(Mockito.eq(code))).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        Product response = productListingService.updateProduct(code, product);
        Assertions.assertEquals(id, response.getId());
    }
    
    @Test
    public void testUpdateProduct_throwNotFoundError() {
        Mockito.when(productRepository.findByCode(Mockito.eq(code))).thenReturn(Optional.empty());
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingService.updateProduct(code, product));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testGetProduct() {
        Mockito.when(productRepository.findByCode(Mockito.eq(code))).thenReturn(Optional.of(product));
        Product response = productListingService.getProduct(code);
        Assertions.assertEquals(id, response.getId());
    }
    
    @Test
    public void testGetProduct_throwNotFoundError() {
        Mockito.when(productRepository.findByCode(Mockito.eq(code))).thenReturn(Optional.empty());
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingService.getProduct(code));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testListProducts() {
        Mockito.when(productRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(Arrays.asList(product), PageRequest.of(page, size), totalSize));
        Page<Product> response = productListingService.listProducts(page, size);
        Assertions.assertEquals(id, response.getContent().get(0).getId());
        Assertions.assertEquals(page, response.getPageable().getPageNumber());
        Assertions.assertEquals(size, response.getPageable().getPageSize());
    }
    
    @Test
    public void testGetProduct_throwInternalError() {
        Mockito.when(productRepository.findAll(Mockito.any(PageRequest.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(Exception.class, () -> productListingService.listProducts(page, size));
    }
    
    @Test
    public void testDeleteProduct() {
        Mockito.when(productRepository.existsByCode(Mockito.eq(code))).thenReturn(true);
        Mockito.doNothing().when(productRepository).deleteByCode(code);
        Assertions.assertAll(() -> productListingService.deleteProduct(code));
    }
    
    @Test
    public void testDeleteProduct_throwNotFoundError() {
        Mockito.when(productRepository.existsByCode(Mockito.eq(code))).thenReturn(false);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingService.deleteProduct(code));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
