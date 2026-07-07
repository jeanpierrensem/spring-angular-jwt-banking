package dev.soft.bankingapp.services;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.mappers.*;
import dev.soft.bankingapp.repositories.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;


    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer saveCustomer = customerRepository.save(dtoMapper.fromCustomerDTO(customerDTO));
        return dtoMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("customer not found");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCurrency("€");
        currentAccount.setCustomer(customer);
        currentAccount.setOverdraft(overdraft);
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedCurrentAccount);


    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("customer not found");

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCurrency("€");
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingAccount(savedSavingAccount);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<CustomerDTO> customerDTOs = customerRepository.findAll().stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOs;
    }

    @Override
    public List<BankAccountDTO> listBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = new ArrayList<>();
        bankAccounts.forEach(bankAcc -> {
            if (bankAcc instanceof SavingAccount) {
                SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
                BeanUtils.copyProperties(((SavingAccount) bankAcc), savingBankAccountDTO);
                savingBankAccountDTO.setCustomerDTO(dtoMapper.fromCustomer(bankAcc.getCustomer()));
                savingBankAccountDTO.setType(savingBankAccountDTO.getClass().getSimpleName());
                bankAccountDTOS.add(savingBankAccountDTO);
            } else if (bankAcc instanceof CurrentAccount) {
                CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
                BeanUtils.copyProperties(((CurrentAccount) bankAcc), currentBankAccountDTO);
                currentBankAccountDTO.setCustomerDTO(dtoMapper.fromCustomer(bankAcc.getCustomer()));
                currentBankAccountDTO.setType(currentBankAccountDTO.getClass().getSimpleName());
                bankAccountDTOS.add(currentBankAccountDTO);
            }
        });
        return bankAccountDTOS;
    }

    public BankAccountDTO getBankAccount(String accountId) throws AccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (bankAccount instanceof SavingAccount) {
            return dtoMapper.fromSavingAccount((SavingAccount) bankAccount);
        }
        return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws AccountNotFoundException, AccountBalanceNotSufficientException {
        //account search
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        System.out.println("account to debit ="+ bankAccount.getId());

        if (bankAccount.getBalance() < amount) {
            throw new AccountBalanceNotSufficientException("Account Balance Not Sufficient");
        }
        Operation operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws AccountNotFoundException {
        //account search
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Operation operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, AccountBalanceNotSufficientException {
        System.out.println("accountIdSource ="+ accountIdSource);
        System.out.println("accountIdDestination ="+ accountIdDestination);
        System.out.println("amount ="+ amount);

        debit(accountIdSource, amount, "Transfer to" + accountIdDestination);
        credit(accountIdDestination, amount, "Tranfer from " + accountIdSource);
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        return dtoMapper.fromCustomer(customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customr Not Found")));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Update existing customer");
        Customer saveCustomer = customerRepository.save(dtoMapper.fromCustomerDTO(customerDTO));
        return dtoMapper.fromCustomer(saveCustomer);

    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<CustomerDTO> findCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByNameContainingIgnoreCase(keyword);
        List<CustomerDTO> customerDTOS =  customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
         return customerDTOS;
    }


    @Override
    public List<AccountOperationDTO> getAccountHistory(String accountId) throws AccountNotFoundException {
        List<Operation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByDateDesc(accountId);
        List<AccountOperationDTO> AccountOperationDTOs = accountOperations.stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        return AccountOperationDTOs;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNoFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount ==null) throw  new BankAccountNoFoundException("Account not Found") ;
        Page<Operation>  accountOperations = accountOperationRepository.findByBankAccountIdOrderByDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO =  new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTO = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTO(accountOperationDTO);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
       return  accountHistoryDTO ;
    }
}
