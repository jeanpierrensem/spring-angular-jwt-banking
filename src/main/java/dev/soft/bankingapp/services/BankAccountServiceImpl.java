package dev.soft.bankingapp.services;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.repositories.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.hibernate.boot.jaxb.hbm.spi.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private OperationRepository operationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new customer");
        return customerRepository.save(customer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null)
               throw new CustomerNotFoundException("customer not found");

        CurrentAccount currentAccount   = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCurrency("€");
        currentAccount.setCustomer(customer);
        currentAccount.setOverdraft(overdraft);
        currentAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(currentAccount);

    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFoundException("customer not found");

        SavingAccount savingAccount    = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCurrency("€");
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(savingAccount);
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<BankAccount> listBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws AccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new AccountNotFoundException("Account not found"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws AccountNotFoundException, AccountBalanceNotSufficientException {
        //recherche le compte
        BankAccount bankAccount = getBankAccount(accountId);

        if (bankAccount.getBalance() < amount) {
            throw new AccountBalanceNotSufficientException("Account Balance Not Sufficient");
        }
        Operation  operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws AccountNotFoundException {
        //recherche le compte
        BankAccount bankAccount = getBankAccount(accountId);
        Operation  operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, AccountBalanceNotSufficientException {
        BankAccount sourcebankAccount = getBankAccount(accountIdSource);
        BankAccount destinationbankAccount = getBankAccount(accountIdDestination);
        if (sourcebankAccount.getBalance() < amount) {
            throw new AccountBalanceNotSufficientException("Account Balance Not Sufficient");
        }

        debit(accountIdSource, amount, "Transfer to" + accountIdDestination);
        credit(accountIdDestination, amount, "Tranfer from "+ accountIdSource);

        sourcebankAccount.setBalance(sourcebankAccount.getBalance() - amount);
        destinationbankAccount.setBalance(destinationbankAccount.getBalance() + amount);

        bankAccountRepository.save(sourcebankAccount);
        bankAccountRepository.save(destinationbankAccount);

    }
}
