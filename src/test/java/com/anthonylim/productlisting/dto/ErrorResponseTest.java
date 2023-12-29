package com.anthonylim.productlisting.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ErrorResponseTest {
    int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    String message = "Internal server errror";
    ErrorDTO error = new ErrorDTO(code, message);
    
    @Test
    public void testSetError() {
        ErrorResponse errorResponse = new ErrorResponse(error);
        int newCode = HttpStatus.BAD_REQUEST.value();
        String newMessage = "Name cannot be null/empty";
        ErrorDTO newError = new ErrorDTO(newCode, newMessage);
        errorResponse.setError(newError);
        Assertions.assertEquals(newCode, errorResponse.getError().getCode());
        Assertions.assertEquals(newMessage, errorResponse.getError().getMessage());
    }
}
