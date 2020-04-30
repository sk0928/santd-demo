package com.santd.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class MovieControllerAdvice {


    @ExceptionHandler({ServletRequestBindingException.class})
    public Object handleException(ServletRequestBindingException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest();
    }
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void movieNotFound(EntityNotFoundException e){
        log.error(e.getMessage());
    }
}

