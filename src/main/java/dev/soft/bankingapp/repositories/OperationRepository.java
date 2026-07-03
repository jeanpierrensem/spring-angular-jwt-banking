package dev.soft.bankingapp.repositories;

import dev.soft.bankingapp.entities.*;
import org.hibernate.query.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.awt.print.*;
import java.util.*;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(Long accountId);
    Page findByBankAccountId(Long accountId, Pageable peageable);
}
