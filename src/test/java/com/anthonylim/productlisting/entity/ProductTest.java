package com.anthonylim.productlisting.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {
    int id = 11;
    String code = "P011";
    String name = "REALME 5 PRO 6+ 128GB";
    String category = "Mobile & Gadgets";
    String brand = "Realme";
    String type = "Mobile Phones";
    String description = "REALME 5 PRO 6+ 128GB";
    
    @Test
    public void testProduct() {
        Product response = new Product(code, name, category, brand, type, description);
        Assertions.assertEquals(code, response.getCode());
        Assertions.assertEquals(name, response.getName());
        Assertions.assertEquals(category, response.getCategory());
        Assertions.assertEquals(brand, response.getBrand());
        Assertions.assertEquals(type, response.getType());
        Assertions.assertEquals(description, response.getDescription());
        
        Assertions.assertAll(() -> new Product());
    }
    
    @Test
    public void testSetId() {
        Product product = new Product();
        product.setId(id);
        Assertions.assertEquals(id, product.getId());
    }
    
    @Test
    public void testSetCode() {
        Product product = new Product();
        product.setCode(code);
        Assertions.assertEquals(code, product.getCode());
    }
    
    @Test
    public void testSetName() {
        Product product = new Product();
        product.setName(name);
        Assertions.assertEquals(name, product.getName());
    }
    
    @Test
    public void testSetCategory() {
        Product product = new Product();
        product.setCategory(category);
        Assertions.assertEquals(category, product.getCategory());
    }
    
    @Test
    public void testSetBrand() {
        Product product = new Product();
        product.setBrand(brand);
        Assertions.assertEquals(brand, product.getBrand());
    }
    
    @Test
    public void testSetType() {
        Product product = new Product();
        product.setType(type);
        Assertions.assertEquals(type, product.getType());
    }
    
    @Test
    public void testSetDescription() {
        Product product = new Product();
        product.setDescription(description);
        Assertions.assertEquals(description, product.getDescription());
    }
}
