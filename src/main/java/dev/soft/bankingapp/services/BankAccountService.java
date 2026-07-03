package dev.soft.bankingapp.services;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.exceptions.*;

import java.util.*;

public interface BankAccountService {


    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long  customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long  customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers();
    List<BankAccountDTO> listBankAccounts();
    BankAccountDTO getBankAccount(String accountId) throws AccountNotFoundException;
    void debit (String accountId, double amount, String description) throws AccountNotFoundException, AccountBalanceNotSufficientException;
    void credit (String accountId, double amount, String description) throws AccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, AccountBalanceNotSufficientException;
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;
}
