package com.anthonylim.productlisting.exception.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.anthonylim.productlisting.dto.ErrorResponse;
import com.anthonylim.productlisting.exception.ProductListingException;

public class ProductListingExceptionHandlerTest {
    private ProductListingExceptionHandler productListingExceptionHandler = new ProductListingExceptionHandler();
    
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Internal server error";
    Throwable cause = new RuntimeException();
    ProductListingException productListingException = new ProductListingException(status, message, cause);
    
    @Test
    public void testHandleProductListingException() {
        ResponseEntity<ErrorResponse> response = productListingExceptionHandler.handleProductListingException(productListingException);
        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertEquals(status.value(), response.getBody().getError().getCode());
        Assertions.assertEquals(message, response.getBody().getError().getMessage());
    }
}
