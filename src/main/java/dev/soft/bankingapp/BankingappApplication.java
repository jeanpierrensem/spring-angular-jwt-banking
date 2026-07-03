package dev.soft.bankingapp;

import dev.soft.bankingapp.entities.*;
import dev.soft.bankingapp.enums.*;
import dev.soft.bankingapp.repositories.*;
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
    CommandLineRunner staticCommandLineRunner(CustomerRepository customerRepository,
										  BankAccountRepository bankAccountRepository,
										  OperationRepository operationRepository
	)

	{
		return args -> {


			/*Stream.of("PAUL", "BERTIN", "MAGELAN").forEach(name -> {
				//customer
			   Customer customer = Customer.builder()
					   .name(name)
					   .email(name.concat("@gmail.com")).build();
			   customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(customer -> {

				CurrentAccount currentAccount =  new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setOverdraft(1000.0);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setCurrency("EURO");
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer( customer );
				bankAccountRepository.save(currentAccount);


				SavingAccount savingAccount =  new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setInterestRate(5.5);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCurrency("EURO");
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer( customer );
				bankAccountRepository.save(savingAccount);
			});

			//Operation
			bankAccountRepository.findAll().forEach(bankAccount -> {

				for(int i=0; i<10; i++){
					Operation operation = new Operation();
					operation.setAmount(Math.random()*9000);
					operation.setDate(new Date());
					operation.setType(Math.random()>0.5 ? OperationType.DEBIT:  OperationType.CREDIT);
					operation.setBankAccount(bankAccount);
					operationRepository.save(operation);
				}


			});*/

			//get an account 0519b6d0-9958-4227-a55d-982c3a17479c
		  BankAccount account = 	bankAccountRepository.findById("0fed500b-b1f2-49e9-bddd-5358df062417").orElseThrow(null);
           if(account != null){
			   System.out.println("===========Bank Account Details============= ");
			   System.out.println(account.getId());
			   System.out.println(account.getBalance());
			   System.out.println(account.getCustomer().getName());
			   System.out.println(account.getCreatedAt());
			   if(account instanceof  SavingAccount ){
				   System.out.println(((SavingAccount)account).getInterestRate());
			   } else if (account instanceof CurrentAccount ){
				   System.out.println(((CurrentAccount)account).getOverdraft());

			   }
			   System.out.println("===========Account Operation Details===============");
			   account.getOperations().forEach(operation -> {
                System.out.println(operation.getDate()+"\t"+operation.getType()+"\t"+operation.getBankAccount().getId()+"\t"+operation.getDate());

			   });
		   }

		};
}
}
