package dev.soft.bankingapp.services;

import dev.soft.bankingapp.dtos.CustomerDTO;
import dev.soft.bankingapp.entities.*;
import org.springframework.beans.*;
import org.springframework.stereotype.*;

@Service
public class BankAccountMapperImpl {
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

}
