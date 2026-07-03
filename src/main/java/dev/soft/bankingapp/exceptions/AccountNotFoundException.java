package dev.soft.bankingapp.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String msg) {

        super(msg);
    }
}
