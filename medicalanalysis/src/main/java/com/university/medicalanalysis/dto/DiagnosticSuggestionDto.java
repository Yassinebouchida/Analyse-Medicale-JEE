package com.university.medicalanalysis.dto;

import lombok.Data;

@Data
public class DiagnosticSuggestionDto {
    private String diseaseName;
    private Double probability;
    private String description;
}
