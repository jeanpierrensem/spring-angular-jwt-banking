package dev.soft.bankingapp.dtos;

import lombok.*;

@Data
public class TransferDTO {
    public String accountSource;
    private String accountDestination;
    private double amount;
}
