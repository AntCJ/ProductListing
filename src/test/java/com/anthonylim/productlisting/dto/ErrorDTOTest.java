package com.anthonylim.productlisting.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ErrorDTOTest {
    int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    String message = "Internal server errror";
    
    @Test
    public void testSetCode() {
        ErrorDTO errorDTO = new ErrorDTO(code, message);
        int newCode = HttpStatus.BAD_REQUEST.value();
        errorDTO.setCode(newCode);
        Assertions.assertEquals(newCode, errorDTO.getCode());
    }
    
    @Test
    public void testSetMessage() {
        ErrorDTO errorDTO = new ErrorDTO(code, message);
        String newMessage = "Name cannot be null/empty";
        errorDTO.setMessage(newMessage);
        Assertions.assertEquals(newMessage, errorDTO.getMessage());
    }
}
