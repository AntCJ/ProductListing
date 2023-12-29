package com.anthonylim.productlisting.controller;

import java.util.Arrays;
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
import org.springframework.http.ResponseEntity;
import com.anthonylim.productlisting.dto.ProductDTO;
import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;
import com.anthonylim.productlisting.service.ProductListingService;

@ExtendWith(MockitoExtension.class)
public class ProductListingControllerTest {
    private ProductListingController productListingController;
    
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
    ProductListingService productListingService;
    
    @BeforeEach
    public void beforeEach() {
        productListingController = new ProductListingController(productListingService);
    }
    
    @Test
    public void testCreateProduct() {
        Mockito.when(productListingService.createProduct(Mockito.any())).thenReturn(product);
        ResponseEntity<ProductDTO> response = productListingController.createProduct(productDTO);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(id, response.getBody().getId());
    }
    
    @Test
    public void testCreateProduct_throwBadRequestError() {
        ProductDTO invalidProductDTO = new ProductDTO(id, code, null, category, brand, type, description);
        ProductListingException invalidProductException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.createProduct(invalidProductDTO));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, invalidProductException.getStatus());
    }
    
    @Test
    public void testCreateProduct_throwConflictError() {
        Mockito.when(productListingService.createProduct(Mockito.any())).thenThrow(new ProductListingException(HttpStatus.CONFLICT, "Product with the same code already exists", null));
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.createProduct(productDTO));
        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }
    
    @Test
    public void testCreateProduct_throwInternalError() {
        Mockito.when(productListingService.createProduct(Mockito.any())).thenThrow(RuntimeException.class);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.createProduct(productDTO));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
    
    @Test
    public void testUpdateProduct() {
        Mockito.when(productListingService.updateProduct(Mockito.eq(code), Mockito.any())).thenReturn(product);
        ResponseEntity<ProductDTO> response = productListingController.updateProduct(code, productDTO);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(id, response.getBody().getId());
    }
    
    @Test
    public void testUpdateProduct_throwBadRequestError() {
        ProductDTO invalidProductDTO = new ProductDTO(id, code, null, category, brand, type, description);
        ProductListingException invalidProductException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(code, invalidProductDTO));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, invalidProductException.getStatus());
        
        ProductListingException nullCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(null, productDTO));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullCodeException.getStatus());
        
        ProductListingException emptyCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(" ", productDTO));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyCodeException.getStatus());
        
        String differentCode = "P099";
        ProductListingException differentCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(differentCode, productDTO));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, differentCodeException.getStatus());
    }
    
    @Test
    public void testUpdateProduct_throwNotFoundError() {
        Mockito.when(productListingService.updateProduct(Mockito.eq(code), Mockito.any())).thenThrow(new ProductListingException(HttpStatus.NOT_FOUND,  "Product not found", null));
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(code, productDTO));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testUpdateProduct_throwInternalError() {
        Mockito.when(productListingService.updateProduct(Mockito.eq(code), Mockito.any())).thenThrow(RuntimeException.class);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.updateProduct(code, productDTO));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
    
    @Test
    public void testGetProduct() {
        Mockito.when(productListingService.getProduct(Mockito.eq(code))).thenReturn(product);
        ResponseEntity<ProductDTO> response = productListingController.getProduct(code);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(id, response.getBody().getId());
    }
    
    @Test
    public void testGetProduct_throwBadRequestError() {
        ProductListingException nullCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.getProduct(null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullCodeException.getStatus());
        
        ProductListingException emptyCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.getProduct(" "));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyCodeException.getStatus());
    }
    
    @Test
    public void testGetProduct_throwNotFoundError() {
        Mockito.when(productListingService.getProduct(Mockito.eq(code))).thenThrow(new ProductListingException(HttpStatus.NOT_FOUND,  "Product not found", null));
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.getProduct(code));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testGetProduct_throwInternalError() {
        Mockito.when(productListingService.getProduct(Mockito.eq(code))).thenThrow(RuntimeException.class);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.getProduct(code));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
    
    @Test
    public void testListProducts() {
        Mockito.when(productListingService.listProducts(Mockito.eq(page), Mockito.eq(size))).thenReturn(new PageImpl<>(Arrays.asList(product), PageRequest.of(page, size), totalSize));
        ResponseEntity<Page<ProductDTO>> response = productListingController.listProducts(page + "", size + "");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(id, response.getBody().getContent().get(0).getId());
        Assertions.assertEquals(totalSize, response.getBody().getNumberOfElements());
        
        Mockito.when(productListingService.listProducts(Mockito.eq(0), Mockito.eq(ProductListingController.DEFAULT_PRODUCT_LIST_PAGE_SIZE))).thenReturn(new PageImpl<>(Arrays.asList(product), PageRequest.of(0, ProductListingController.DEFAULT_PRODUCT_LIST_PAGE_SIZE), totalSize));
        ResponseEntity<Page<ProductDTO>> nullParamsResponse = productListingController.listProducts(null, null);
        Assertions.assertEquals(HttpStatus.OK, nullParamsResponse.getStatusCode());
        Assertions.assertEquals(id, nullParamsResponse.getBody().getContent().get(0).getId());
        Assertions.assertEquals(totalSize, nullParamsResponse.getBody().getNumberOfElements());
        Assertions.assertEquals(0, nullParamsResponse.getBody().getPageable().getPageNumber());
        Assertions.assertEquals(ProductListingController.DEFAULT_PRODUCT_LIST_PAGE_SIZE, nullParamsResponse.getBody().getPageable().getPageSize());
        
        ResponseEntity<Page<ProductDTO>> emptyParamsResponse = productListingController.listProducts(" ", " ");
        Assertions.assertEquals(HttpStatus.OK, emptyParamsResponse.getStatusCode());
        Assertions.assertEquals(id, emptyParamsResponse.getBody().getContent().get(0).getId());
        Assertions.assertEquals(totalSize, emptyParamsResponse.getBody().getNumberOfElements());
        Assertions.assertEquals(0, emptyParamsResponse.getBody().getPageable().getPageNumber());
        Assertions.assertEquals(ProductListingController.DEFAULT_PRODUCT_LIST_PAGE_SIZE, emptyParamsResponse.getBody().getPageable().getPageSize());
    }
    
    @Test
    public void testListProducts_throwBadRequestError() {
        String invalidPage = "page1";
        ProductListingException invalidPageException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.listProducts(invalidPage, size + ""));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, invalidPageException.getStatus());
        
        String invalidSize = "10 items";
        ProductListingException invalidSizeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.listProducts(page + "", invalidSize));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, invalidSizeException.getStatus());
        
        int exceedMaxSize = ProductListingController.MAX_PRODUCT_LIST_PAGE_SIZE + 1;
        ProductListingException exceedMaxSizeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.listProducts(page + "", exceedMaxSize + ""));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exceedMaxSizeException.getStatus());
    }
    
    @Test
    public void testListProducts_throwInternalError() {
        Mockito.when(productListingService.listProducts(Mockito.eq(page), Mockito.eq(size))).thenThrow(RuntimeException.class);
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.listProducts(page + "", size + ""));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
    
    @Test
    public void testDeleteProduct() {
        Mockito.doNothing().when(productListingService).deleteProduct(Mockito.eq(code));
        ResponseEntity<Void> response = productListingController.deleteProduct(code);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testDeleteProduct_throwBadRequestError() {
        ProductListingException nullCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.deleteProduct(null));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullCodeException.getStatus());
        
        ProductListingException emptyCodeException = Assertions.assertThrows(ProductListingException.class, () -> productListingController.deleteProduct(" "));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyCodeException.getStatus());
    }
    
    @Test
    public void testDeleteProduct_throwNotFoundError() {
        Mockito.doThrow(new ProductListingException(HttpStatus.NOT_FOUND,  "Product not found", null)).when(productListingService).deleteProduct(Mockito.eq(code));
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.deleteProduct(code));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testDeleteProduct_throwInternalError() {
        Mockito.doThrow(RuntimeException.class).when(productListingService).deleteProduct(Mockito.eq(code));
        ProductListingException exception = Assertions.assertThrows(ProductListingException.class, () -> productListingController.deleteProduct(code));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
}
