package dev.soft.bankingapp.dtos;

import lombok.*;

@Data
public class CreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
