package com.jahid.ecommerce.api.utility;

import com.jahid.ecommerce.api.user.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(NotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getClassName()+" Not Found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ApiErrorResponse> handlePasswordNotMatch(){
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,"Password error");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
