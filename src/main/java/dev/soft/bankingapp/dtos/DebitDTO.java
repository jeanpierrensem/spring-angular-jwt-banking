package dev.soft.bankingapp.dtos;

import lombok.*;

@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
