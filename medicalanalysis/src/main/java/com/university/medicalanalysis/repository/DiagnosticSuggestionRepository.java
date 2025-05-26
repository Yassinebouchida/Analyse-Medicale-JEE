package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.DiagnosticSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticSuggestionRepository extends JpaRepository<DiagnosticSuggestion, Long> {
}
