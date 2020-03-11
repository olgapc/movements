package com.movements.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.movements.app.controllers.SearchController;
import com.movements.app.models.service.EmployeeServiceImpl;

@SpringBootApplication
public class SpringMovementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMovementsApplication.class, args);
		
	}

}
