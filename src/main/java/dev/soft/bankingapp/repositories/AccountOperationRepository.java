package dev.soft.bankingapp.repositories;

import dev.soft.bankingapp.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.awt.print.*;
import java.util.*;

@Repository
public interface AccountOperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(String accountId);
    Page<Operation> findByBankAccountId(String accountId, PageRequest pageRequest);
}
