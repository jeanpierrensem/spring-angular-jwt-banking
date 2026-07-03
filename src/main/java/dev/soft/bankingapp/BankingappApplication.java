package dev.soft.bankingapp;

import dev.soft.bankingapp.dtos.*;
import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.exceptions.*;
import dev.soft.bankingapp.repositories.*;
import dev.soft.bankingapp.services.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import java.util.*;
import java.util.stream.*;

@SpringBootApplication
public class BankingappApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingappApplication.class, args);
	}

	@Bean
	CommandLineRunner staticCommandLineRunner(BankAccountService bankAccountService) {
		return args -> {
			//create customer
			Stream.of("Bertin", "Jean-Pierre", "Roger").forEach(name -> {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customerDTO);
			});

			//for each customer, create currentAccount and savingAccount
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 10000, Math.random() * 500, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 5600, Math.random() * 200, customer.getId());
				List<BankAccountDTO> bankAccountList = bankAccountService.listBankAccounts();

				for (BankAccountDTO bankAccountDTO : bankAccountList) {
					System.out.println("Item " + bankAccountDTO.getClass().getName());
					for (int i = 0; i < 10; i++) {
						if (bankAccountDTO instanceof SavingBankAccountDTO) {
								bankAccountService.credit(((SavingBankAccountDTO) bankAccountDTO).getId(), 10000 + Math.random(), "credit");
								bankAccountService.debit(((SavingBankAccountDTO) bankAccountDTO).getId(), 1000 + Math.random(), "Debit");
						} else {
							bankAccountService.credit(((CurrentBankAccountDTO) bankAccountDTO).getId(), 10000 + Math.random(), "credit");
							bankAccountService.debit(((CurrentBankAccountDTO) bankAccountDTO).getId(), 1000 + Math.random(), "Debit");
						}
					}
				}
				} catch (CustomerNotFoundException  | AccountNotFoundException | AccountBalanceNotSufficientException e) {
					throw new RuntimeException(e);
				}
			});

		};
	}
	}
