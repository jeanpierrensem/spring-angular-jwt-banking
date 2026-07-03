package dev.soft.bankingapp.repositories;

import dev.soft.bankingapp.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
