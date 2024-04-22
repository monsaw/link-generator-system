package com.hommies.linkgeneratorsystem.exceptions;

public class PaymentNotExistException extends RuntimeException{

    public PaymentNotExistException(String message) {
        super(message);
    }
}
