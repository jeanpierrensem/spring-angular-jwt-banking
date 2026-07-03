package dev.soft.bankingapp.dtos;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Data
public class AccountOperationDTO {
    private Long id ;
    private Date date;
    private double amount;
    private OperationType type;
    private String description;
    private BankAccountDTO bankAccountDTO;
}
