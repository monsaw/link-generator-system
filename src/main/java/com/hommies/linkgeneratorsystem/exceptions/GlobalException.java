package com.hommies.linkgeneratorsystem.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler( CompanyAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlerForCompanyAlreadyExistException(final  CompanyAlreadyExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" A merchant with this company code already exist, try another code !!");
        errorResponse.setStatus(HttpStatus.CONFLICT);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( PaymentNotExistException.class)
    public ResponseEntity<ErrorResponse> handlerForPaymentNotExistException(final  PaymentNotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" Payment does not exist..!!");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( CompanyNotExistException.class)
    public ResponseEntity<ErrorResponse> handlerForCompanyDoesNotExistException(final CompanyNotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" A merchant with this company code does not exist!!");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( InvoiceNotExistException.class)
    public ResponseEntity<ErrorResponse> handlerForInvoiceNotExistException(final  InvoiceNotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" This invoice does not exist!!");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( TransactionInvoiceNotExistException.class)
    public ResponseEntity<ErrorResponse> handlerForTransactionInvoiceNotExistException(final  TransactionInvoiceNotExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" Transaction Invoice does not exist!!");
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( TransactionException.class)
    public ResponseEntity<ErrorResponse> handlerForTransactionException(final  TransactionException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" Reach out to provider to generate new link !!");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler( PaymentException.class)
    public ResponseEntity<ErrorResponse> handlerForPaymentException(final  PaymentException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" Payment not successful !!");
        errorResponse.setStatus(HttpStatus.CONFLICT);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( PaymentAmountException.class)
    public ResponseEntity<ErrorResponse> handlerForPaymentAmountException(final  PaymentAmountException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setDebugMessage(" Amount to paid is not equal to expected payment !!");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setDate(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
