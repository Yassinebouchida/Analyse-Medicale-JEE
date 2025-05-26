package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
