package com.lahssini.Tp3MVCThylemeaf;

import com.lahssini.Tp3MVCThylemeaf.entities.Patient;
import com.lahssini.Tp3MVCThylemeaf.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Tp3MvcThylemeafApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(Tp3MvcThylemeafApplication.class, args);
		System.out.println("hanane");
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		//SOLUTION 1 CONSTRUCTEUR SANS PARAM
		Patient patient=new Patient();
		patient.setNom("hanane");
		patient.setDateNaissance(new Date());
		patient.setMalade(false);
		patient.setScore(10);
		//SOLUTION 2 CONSTRUCTEUR AVEC PARAM
		Patient patient2=new Patient(null,"janat",new Date(),false,2);
		//SOLUTION 3 BUILDER
		Patient patient3=Patient.builder()
				.nom("jad")
				.dateNaissance(new Date())
				.score(3)
				.malade(true)
				.build();
		*/

		patientRepository.save(new Patient(null,"Mohamed",new Date(),false,142));
		patientRepository.save(new Patient(null,"Imane",new Date(),true,198));
		patientRepository.save(new Patient(null,"Yassine",new Date(),true,342));
		patientRepository.save(new Patient(null,"Laila",new Date(),false,123));



	}
}
