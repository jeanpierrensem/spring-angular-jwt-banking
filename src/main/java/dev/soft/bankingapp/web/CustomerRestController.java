package dev.soft.bankingapp.web;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.services.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@AllArgsConstructor
public class CustomerRestController {
    BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<Customer> customers() {
       return  bankAccountService.listCustomers();
    }

}
