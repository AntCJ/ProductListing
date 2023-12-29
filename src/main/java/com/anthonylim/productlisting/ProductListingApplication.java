package com.anthonylim.productlisting;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.anthonylim.productlisting"})
public class ProductListingApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(ProductListingApplication.class, args);
    }
}
