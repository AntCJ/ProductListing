package com.anthonylim.productlisting.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;

public class ProductDTOTest {
    int id = 11;
    String code = "P011";
    String name = "REALME 5 PRO 6+ 128GB";
    String category = "Mobile & Gadgets";
    String brand = "Realme";
    String type = "Mobile Phones";
    String description = "REALME 5 PRO 6+ 128GB";
    ProductDTO productDTO = new ProductDTO(id, code, name, category, brand, type, description);
    Product product = new Product(id, code, name, category, brand, type, description);
    
    @Test
    public void testSetId() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        Assertions.assertEquals(id, productDTO.getId());
    }
    
    @Test
    public void testSetCode() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode(code);
        Assertions.assertEquals(code, productDTO.getCode());
    }
    
    @Test
    public void testSetName() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        Assertions.assertEquals(name, productDTO.getName());
    }
    
    @Test
    public void testSetCategory() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory(category);
        Assertions.assertEquals(category, productDTO.getCategory());
    }
    
    @Test
    public void testSetBrand() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setBrand(brand);
        Assertions.assertEquals(brand, productDTO.getBrand());
    }
    
    @Test
    public void testSetType() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setType(type);
        Assertions.assertEquals(type, productDTO.getType());
    }
    
    @Test
    public void testSetDescription() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription(description);
        Assertions.assertEquals(description, productDTO.getDescription());
    }
    
    @Test
    public void testToDTO() {
        ProductDTO response = ProductDTO.toDTO(product);
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(code, response.getCode());
        Assertions.assertEquals(name, response.getName());
        Assertions.assertEquals(category, response.getCategory());
        Assertions.assertEquals(brand, response.getBrand());
        Assertions.assertEquals(type, response.getType());
        Assertions.assertEquals(description, response.getDescription());
    }
    
    @Test
    public void testToEntity() {
        Product response = ProductDTO.toEntity(productDTO);
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(code, response.getCode());
        Assertions.assertEquals(name, response.getName());
        Assertions.assertEquals(category, response.getCategory());
        Assertions.assertEquals(brand, response.getBrand());
        Assertions.assertEquals(type, response.getType());
        Assertions.assertEquals(description, response.getDescription());
    }
    
    @Test
    public void testValidate() {
        Assertions.assertAll(() -> productDTO.validate());
    }
    
    @Test
    public void testValidate_throwBadRequestError() {
        ProductDTO nullCodeProductDTO = new ProductDTO(id, null, name, category, brand, type, description);
        ProductListingException nullCodeException = Assertions.assertThrows(ProductListingException.class, () -> nullCodeProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullCodeException.getStatus());
        
        ProductDTO emptyCodeProductDTO = new ProductDTO(id, " ", name, category, brand, type, description);
        ProductListingException emptyCodeException = Assertions.assertThrows(ProductListingException.class, () -> emptyCodeProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyCodeException.getStatus());
        
        ProductDTO nullNameProductDTO = new ProductDTO(id, code, null, category, brand, type, description);
        ProductListingException nullNameException = Assertions.assertThrows(ProductListingException.class, () -> nullNameProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullNameException.getStatus());
        
        ProductDTO emptyNameProductDTO = new ProductDTO(id, code, " ", category, brand, type, description);
        ProductListingException emptyNameException = Assertions.assertThrows(ProductListingException.class, () -> emptyNameProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyNameException.getStatus());
        
        ProductDTO nullCategoryProductDTO = new ProductDTO(id, code, name, null, brand, type, description);
        ProductListingException nullCategoryException = Assertions.assertThrows(ProductListingException.class, () -> nullCategoryProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, nullCategoryException.getStatus());
        
        ProductDTO emptyCategoryProductDTO = new ProductDTO(id, code, name, " ", brand, type, description);
        ProductListingException emptyCategoryException = Assertions.assertThrows(ProductListingException.class, () -> emptyCategoryProductDTO.validate());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, emptyCategoryException.getStatus());
    }
 }
