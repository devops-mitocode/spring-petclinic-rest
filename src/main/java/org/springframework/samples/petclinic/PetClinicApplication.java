package org.springframework.samples.petclinic;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PetClinicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(PetClinicApplication.class, args);

        DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "123456");
       
        String input = null;

        System.out.println(input.length());
	}
}
