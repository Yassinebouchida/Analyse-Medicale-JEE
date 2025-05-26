package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.MedicalAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalAnalysisRepository extends JpaRepository<MedicalAnalysis, Long> {
}
