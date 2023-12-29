package com.anthonylim.productlisting.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProductListingExceptionTest {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Internal server error";
    Throwable cause = new RuntimeException();
    
    @Test
    public void testProductListingException() {
        ProductListingException exception = new ProductListingException(status, message, cause);
        Assertions.assertEquals(status, exception.getStatus());
        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }
    
    @Test
    public void testSetStatus() {
        ProductListingException exception = new ProductListingException(status, message, cause);
        HttpStatus newStatus = HttpStatus.BAD_REQUEST;
        exception.setStatus(newStatus);
        Assertions.assertEquals(newStatus, exception.getStatus());
    }
}
