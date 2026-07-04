package dev.soft.bankingapp.web;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.services.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
       return  bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> customers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return  bankAccountService.findCustomers(keyword);
    }


    @PostMapping("/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
    return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer( @PathVariable(name = "id") Long customerId,   @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId( customerId );
        return bankAccountService.saveCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }

}
