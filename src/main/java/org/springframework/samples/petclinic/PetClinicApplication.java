package org.springframework.samples.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PetClinicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
		// Additional initialization code can go here
		// For example, you could set up logging or perform initial data loading
		// This is a good place to add any custom startup logic
		// Custom startup logic can be added here

		// For example, you could set up a database connection or load initial data
		// Database connection setup code can go here
		// You could use a DataSource bean to configure the database connection

		// Additional database initialization code can go here
		
	}
}
