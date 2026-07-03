package dev.soft.bankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount{
    private double overdraft;

}
