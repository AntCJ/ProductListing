package com.anthonylim.productlisting.dto;

import org.springframework.http.HttpStatus;

import com.anthonylim.productlisting.entity.Product;
import com.anthonylim.productlisting.exception.ProductListingException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private int id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;
    
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getCode(),
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getType(),
                product.getDescription());
    }
    
    public static Product toEntity(ProductDTO productDTO) {
        return new Product(productDTO.getId(),
                productDTO.getCode(),
                productDTO.getName(),
                productDTO.getCategory(),
                productDTO.getBrand(),
                productDTO.getType(),
                productDTO.getDescription());
    }
    
    public void validate() {
        if (this.code == null || this.code.trim().equals("")) {
            throw new ProductListingException(HttpStatus.BAD_REQUEST,
                    "Code cannot be null/empty",
                    null);
        }
        
        if (this.name == null || this.name.trim().equals("")) {
            throw new ProductListingException(HttpStatus.BAD_REQUEST,
                    "Name cannot be null/empty",
                    null);
        }
        
        if (this.category == null || this.category.trim().equals("")) {
            throw new ProductListingException(HttpStatus.BAD_REQUEST,
                    "Category cannot be null/empty",
                    null);
        }
    }
 }
