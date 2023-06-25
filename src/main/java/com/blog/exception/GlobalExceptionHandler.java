package com.blog.exception;

import com.blog.payload.ErrorDetails;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//it works like catch block
@ControllerAdvice

//inorder to this catch block apply @controllerAdvice. it helps to receive exception, occurs anywhere in the project

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //if you want handel your exception ,what ever class you create to handle the exception that class compulsory extend
    //responseEntityExceptionHandler class

    //it is specific exception
    //response Entity which return back the response to postman
    //resourceNotFoundException class catch the exception and exception is refference variable which hold the exception
    //webRequest present inside method parameter,which send the information to postman(clint)

    // if  any exception occurs because of resourceNot ,then exception come to handleResource through @ControllerAdvice

    //it is specific exception ex.postNotFound,commentNotFound,id notfound etc

    //resourceNotFound is class and exception is refference Variable
    @ExceptionHandler(ResourceNotFoundException.class)//
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    // global exceptions
    //it handled every exception
    //porocess same as specific exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
