package com.enset.tp.appgestionpatient.repositories;

import com.enset.tp.appgestionpatient.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByNomContains(String nom, Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE p.nom LIKE :x")
    Page<Patient> searchByKeyword(String x, Pageable pageable);

}
