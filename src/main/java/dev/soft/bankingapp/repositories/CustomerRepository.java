package dev.soft.bankingapp.repositories;

import dev.soft.bankingapp.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
