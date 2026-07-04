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
@CrossOrigin("*")
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

   @GetMapping("/accounts/{accountId}/operations")
   public List<AccountOperationDTO> getHistory (@PathVariable String accountId) throws AccountNotFoundException {
      return  bankAccountService.getAccountHistory(accountId);
   }

   @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getHistoryPage (
            @PathVariable String accountId,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "5") int size
                                                     ) throws AccountNotFoundException, BankAccountNoFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);

   }

}
