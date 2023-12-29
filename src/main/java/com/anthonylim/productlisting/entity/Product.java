package com.anthonylim.productlisting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "idSequence", initialValue = 17)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    private int id;
    
    @Column(name = "product_code", length = 4, unique = true)
    private String code;
    
    @Column(name = "product_name", length = 255, nullable = false, unique = true)
    private String name;
    
    @Column(name = "product_category", length = 255, nullable = false)
    private String category;
    
    @Column(name = "product_brand", length = 255)
    private String brand;
    
    @Column(name = "product_type", length = 255)
    private String type;
    
    @Column(name = "product_description", length = 255)
    private String description;
    
    public Product(String code, String name, String category, String brand, String type, String description) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.type = type;
        this.description = description;
    }
}
