package com.hommies.linkgeneratorsystem.exceptions;

public class InvoiceNotExistException extends RuntimeException{
    public InvoiceNotExistException(String message) {
        super(message);
    }
}
