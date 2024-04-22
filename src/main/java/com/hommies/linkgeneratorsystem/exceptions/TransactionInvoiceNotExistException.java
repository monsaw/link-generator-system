package com.hommies.linkgeneratorsystem.exceptions;

public class TransactionInvoiceNotExistException extends RuntimeException{
    public TransactionInvoiceNotExistException(String message) {
        super(message);
    }
}
