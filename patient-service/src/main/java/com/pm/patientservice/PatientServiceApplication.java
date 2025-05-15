package com.pm.patientservice;

import com.pm.patientservice.model.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PatientServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}
}
