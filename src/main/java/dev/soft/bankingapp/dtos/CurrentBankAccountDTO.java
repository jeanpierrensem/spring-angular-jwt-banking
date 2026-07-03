package dev.soft.bankingapp.dtos;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import lombok.*;

import java.util.*;

@Data
public  class CurrentBankAccountDTO extends  BankAccountDTO {
    private String id;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customerDTO;
    private double overdraft;
}
