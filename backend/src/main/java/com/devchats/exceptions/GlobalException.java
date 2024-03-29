package com.devchats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

  @ExceptionHandler
  public ResponseEntity<ErrorPojo> handleUserNotFoundException(UserNotFoundException ex){
    ErrorPojo errorPojo =  new ErrorPojo();
    errorPojo.setStatus(HttpStatus.NOT_FOUND.value());
    errorPojo.setMessage(ex.getMessage());
    errorPojo.setTimestamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorPojo, HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler
  public ResponseEntity<ErrorPojo> handleUserErrorException(UserErrorException ex) {
    ErrorPojo errorPojo = new ErrorPojo();
    errorPojo.setStatus(HttpStatus.BAD_REQUEST.value());
    errorPojo.setMessage(ex.getMessage());
    errorPojo.setTimestamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorPojo, HttpStatus.BAD_REQUEST);

  }

    @ExceptionHandler
  public ResponseEntity<ErrorPojo> handleCustomException(PostNotFoundException ex){
    ErrorPojo errorPojo =  new ErrorPojo();
    errorPojo.setStatus(HttpStatus.BAD_REQUEST.value());
    errorPojo.setMessage(ex.getMessage());
    errorPojo.setTimestamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorPojo, HttpStatus.BAD_REQUEST);

  }
  @ExceptionHandler
  public ResponseEntity<ErrorPojo> handlePostNotFoundException(CustomException ex){
    ErrorPojo errorPojo =  new ErrorPojo();
    errorPojo.setStatus(HttpStatus.BAD_REQUEST.value());
    errorPojo.setMessage(ex.getMessage());
    errorPojo.setTimestamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorPojo, HttpStatus.BAD_REQUEST);

  }




}
