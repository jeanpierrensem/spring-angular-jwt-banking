package dev.soft.bankingapp.entities;

import dev.soft.bankingapp.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;


}
