package com.anthonylim.productlisting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListingException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private HttpStatus status;
    
    public ProductListingException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
