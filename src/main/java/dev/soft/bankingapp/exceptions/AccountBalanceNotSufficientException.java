package dev.soft.bankingapp.exceptions;

public class AccountBalanceNotSufficientException extends Exception {
    public AccountBalanceNotSufficientException(String message) {
        super(message);
    }
}
