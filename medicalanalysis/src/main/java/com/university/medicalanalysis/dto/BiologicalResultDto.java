package com.university.medicalanalysis.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class BiologicalResultDto {

    @NotBlank
    private String markerName;

    @NotNull
    private Double value;
}
