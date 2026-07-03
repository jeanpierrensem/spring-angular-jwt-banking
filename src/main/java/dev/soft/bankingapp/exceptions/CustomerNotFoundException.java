package dev.soft.bankingapp.exceptions;

import dev.soft.bankingapp.entities.*;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
