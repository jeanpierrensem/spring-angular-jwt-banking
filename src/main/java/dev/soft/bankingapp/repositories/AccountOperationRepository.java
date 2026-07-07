package dev.soft.bankingapp.repositories;

import dev.soft.bankingapp.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.awt.print.*;
import java.util.*;

@Repository
public interface AccountOperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountIdOrderByDateDesc(String accountId);
    Page<Operation> findByBankAccountIdOrderByDateDesc(String accountId, PageRequest pageRequest);
}
