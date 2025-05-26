package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
}
