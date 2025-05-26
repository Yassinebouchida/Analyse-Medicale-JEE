package com.university.medicalanalysis.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisResponseDto {
    private Long analysisId;
    private String patientName;
    private String date;
    private List<DiagnosticSuggestionDto> suggestions;
}
