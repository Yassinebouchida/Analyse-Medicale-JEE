package com.university.medicalanalysis.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
public class AnalysisRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "format attendu yyyy-MM-dd")
    private String birthDate;

    @NotBlank
    private String gender;

    @Positive
    private Double height;

    @Positive
    private Double weight;

    @Size(min = 1, message = "au moins un symptôme")
    private List<@NotBlank String> symptomNames;

    @Size(min = 1, message = "au moins un résultat biologique")
    private List<@Valid BiologicalResultDto> biologicalResults;
}
