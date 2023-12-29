package com.anthonylim.productlisting.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.anthonylim.productlisting.dto.ErrorDTO;
import com.anthonylim.productlisting.dto.ErrorResponse;
import com.anthonylim.productlisting.exception.ProductListingException;

@ControllerAdvice
public class ProductListingExceptionHandler {
    @ExceptionHandler({ ProductListingException.class })
    public ResponseEntity<ErrorResponse> handleProductListingException(ProductListingException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ErrorResponse(new ErrorDTO(exception.getStatus().value(), exception.getMessage())));
    }
}
