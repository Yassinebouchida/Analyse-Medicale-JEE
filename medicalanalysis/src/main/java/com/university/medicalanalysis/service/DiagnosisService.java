package com.university.medicalanalysis.service;

import com.university.medicalanalysis.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    public List<DiagnosticSuggestion> generateDiagnosticSuggestions(MedicalAnalysis analysis) {

        List<DiagnosticSuggestion> suggestions = new ArrayList<>();

        boolean hasFever = analysis.getSymptoms().stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase("Fièvre"));

        boolean hasCough = analysis.getSymptoms().stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase("Toux"));

        boolean hasFatigue = analysis.getSymptoms().stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase("Fatigue"));

        Double crpValue = analysis.getBiologicalResults().stream()
                .filter(r -> r.getMarker().getName().equalsIgnoreCase("CRP"))
                .map(BiologicalResult::getValue)
                .findFirst()
                .orElse(null);

        Double glucose = analysis.getBiologicalResults().stream()
                .filter(r -> r.getMarker().getName().equalsIgnoreCase("Glucose"))
                .map(BiologicalResult::getValue)
                .findFirst()
                .orElse(null);

        if (hasFever && hasCough && (crpValue != null && crpValue > 10)) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Infection virale sévère",
                    0.8,
                    "Fièvre, toux et CRP élevée",
                    analysis
            ));
        } else if (hasFever) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Infection virale",
                    0.7,
                    "Présence de fièvre et symptômes généraux",
                    analysis
            ));
        }

        if (crpValue != null && crpValue > 10) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Inflammation bactérienne possible",
                    0.6,
                    "CRP élevée détectée",
                    analysis
            ));
        }

        if (glucose != null && glucose > 1.4) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Hyperglycémie",
                    0.75,
                    "Taux de glucose élevé",
                    analysis
            ));
        }

        if (hasFatigue && (crpValue == null || crpValue < 10)) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Surcharge physique",
                    0.5,
                    "Fatigue sans signe inflammatoire marqué",
                    analysis
            ));
        }

        if (suggestions.isEmpty()) {
            suggestions.add(new DiagnosticSuggestion(
                    null,
                    "Données insuffisantes",
                    0.3,
                    "Pas assez d'informations pour un diagnostic précis",
                    analysis
            ));
        }

        return suggestions;
    }
}
