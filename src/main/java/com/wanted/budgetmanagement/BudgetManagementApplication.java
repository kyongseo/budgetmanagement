package com.wanted.budgetmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BudgetManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagementApplication.class, args);
	}

}
