package dev.soft.bankingapp.web;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.services.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
   public BankAccountDTO getBankAccount(@PathVariable(name = "accountId") String accountId) throws AccountNotFoundException {
       return bankAccountService.getBankAccount(accountId);
   }
   @GetMapping("/accounts")
   List<BankAccountDTO> getBankAccountList(){
       return bankAccountService.listBankAccounts();
   }

}
