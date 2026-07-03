package dev.soft.bankingapp.services;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.exceptions.*;

import java.util.*;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overdraft,  Long  customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long  customerId) throws CustomerNotFoundException;

    List<Customer> listCustomers();
    List<BankAccount> listBankAccounts();
    BankAccount getBankAccount(String accountId) throws AccountNotFoundException;
    void debit (String accountId, double amount, String description) throws AccountNotFoundException, AccountBalanceNotSufficientException;
    void credit (String accountId, double amount, String description) throws AccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, AccountBalanceNotSufficientException;
}
