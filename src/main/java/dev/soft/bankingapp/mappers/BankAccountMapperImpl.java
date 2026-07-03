package dev.soft.bankingapp.mappers;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import org.springframework.beans.*;
import org.springframework.stereotype.*;

@Service
public class BankAccountMapperImpl {
    /* Customer DTO */
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return  customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer  = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }
    /*BankAccount DTOs */

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
    CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
    BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
    currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));

    return  currentBankAccountDTO;

    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO  currentBankAccountDTO){
    CurrentAccount currentAccount = new CurrentAccount();
    BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
    currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
    return  currentAccount;
    }

    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
    SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
    BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
    savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
    return  savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO  savingBankAccountDTO){
 SavingAccount savingAccount = new SavingAccount();
 BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
 savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
 return  savingAccount;
    }


}
