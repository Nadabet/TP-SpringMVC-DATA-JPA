package com.enset.tp.appgestionpatient;

import com.enset.tp.appgestionpatient.entities.Patient;
import com.enset.tp.appgestionpatient.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.util.Date;

@SpringBootApplication
public class AppGestionPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppGestionPatientApplication.class, args);
    }

    //@Bean
    public CommandLineRunner init(PatientRepository patientRepository) {

        return args -> {
            Patient patient = Patient.builder().nom("Azizo").dateNaissance(new Date()).score(34).malade(false).build();
            patientRepository.save(patient);
        };
    }
}
