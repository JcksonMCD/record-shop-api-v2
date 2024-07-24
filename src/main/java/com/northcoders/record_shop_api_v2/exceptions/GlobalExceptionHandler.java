package com.northcoders.record_shop_api_v2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// Exception interceptor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<ErrorObject> handleAlbumNotFoundException(AlbumNotFoundException ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
}
