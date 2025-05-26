package com.university.medicalanalysis.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MedicalAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private LocalDateTime createdAt;

    /* Relations */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @ToString.Exclude
    private Patient patient;

    @ManyToMany
    @JoinTable(
            name = "analysis_symptoms",
            joinColumns = @JoinColumn(name = "analysis_id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    @ToString.Exclude
    private List<Symptom> symptoms;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BiologicalResult> biologicalResults;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DiagnosticSuggestion> diagnosticSuggestions;
}
