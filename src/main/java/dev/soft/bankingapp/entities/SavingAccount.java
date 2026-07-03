package dev.soft.bankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@DiscriminatorValue("SA")
public class SavingAccount extends  BankAccount{
    private double interestRate;
}
