package com.pringboot.blog.exception;

import com.pringboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    //ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                 WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

//blog app Api exceptions
    @ExceptionHandler(BlogAppApiException.class)
    ResponseEntity<ErrorDetails> handleBlogAppApiException(BlogAppApiException exception,
                                                                 WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }



}
