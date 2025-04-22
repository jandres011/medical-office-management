package edu.project.medicalofficemanagement;

import org.springframework.boot.SpringApplication;

public class TestMedicalOfficeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(MedicalOfficeManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
