package dev.soft.bankingapp.web;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.services.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin("*")
@EnableMethodSecurity(securedEnabled = true)
public class CustomerRestController {
    BankAccountService bankAccountService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
       return  bankAccountService.listCustomers();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/customers/search")
    public List<CustomerDTO> customers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return  bankAccountService.findCustomers(keyword);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
    return bankAccountService.saveCustomer(customerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer( @PathVariable(name = "id") Long customerId,   @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId( customerId );
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }

}
