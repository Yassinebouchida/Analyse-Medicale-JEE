package com.university.medicalanalysis.service;

import com.university.medicalanalysis.dto.*;
import com.university.medicalanalysis.model.*;
import com.university.medicalanalysis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final PatientRepository patientRepository;
    private final SymptomRepository symptomRepository;
    private final BiologicalMarkerRepository markerRepository;
    private final BiologicalResultRepository biologicalResultRepository;
    private final MedicalAnalysisRepository analysisRepository;
    private final DiagnosticSuggestionRepository suggestionRepository;
    private final DiagnosisService diagnosisService;

    public AnalysisResponseDto createAnalysis(AnalysisRequestDto request) {
        // 1. Create and save patient
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setGender(request.getGender());
        patient.setBirthDate(LocalDate.parse(request.getBirthDate()));
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patientRepository.save(patient);

        // 2. Initialize medical analysis
        MedicalAnalysis analysis = new MedicalAnalysis();
        analysis.setCreatedAt(LocalDateTime.now());
        analysis.setPatient(patient);

        // 3. Associate symptoms
        List<Symptom> symptoms = request.getSymptomNames().stream()
                .map(name -> symptomRepository.findAll().stream()
                        .filter(sym -> sym.getName().equalsIgnoreCase(name))
                        .findFirst()
                        .orElseGet(() -> {
                            Symptom newSymptom = new Symptom();
                            newSymptom.setName(name);
                            newSymptom.setSeverity("moyenne");
                            newSymptom.setDescription("Ajout manuel");
                            return symptomRepository.save(newSymptom);
                        })
                ).collect(Collectors.toList());
        analysis.setSymptoms(symptoms);

        // 4. Save early for FK reference
        analysisRepository.save(analysis);

        // 5. Save biological results
        List<BiologicalResult> results = request.getBiologicalResults().stream()
                .map(dto -> {
                    BiologicalMarker marker = markerRepository.findAll().stream()
                            .filter(m -> m.getName().equalsIgnoreCase(dto.getMarkerName()))
                            .findFirst()
                            .orElseGet(() -> {
                                BiologicalMarker newMarker = new BiologicalMarker();
                                newMarker.setName(dto.getMarkerName());
                                newMarker.setUnit("N/A");
                                newMarker.setMinNormalValue(0.0);
                                newMarker.setMaxNormalValue(100.0);
                                return markerRepository.save(newMarker);
                            });

                    BiologicalResult result = new BiologicalResult();
                    result.setMarker(marker);
                    result.setValue(dto.getValue());
                    result.setAnalysis(analysis);
                    return result;
                }).collect(Collectors.toList());

        biologicalResultRepository.saveAll(results);
        analysis.setBiologicalResults(results);

        // 6. Generate and save diagnostic suggestions
        List<DiagnosticSuggestion> suggestions = diagnosisService.generateDiagnosticSuggestions(analysis);
        suggestionRepository.saveAll(suggestions);
        analysis.setDiagnosticSuggestions(suggestions);

        // 7. Final save
        analysisRepository.save(analysis);

        // 8. Prepare DTO response
        AnalysisResponseDto response = new AnalysisResponseDto();
        response.setAnalysisId(analysis.getId());
        response.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        response.setDate(analysis.getCreatedAt().toString());
        response.setSuggestions(
                suggestions.stream().map(s -> {
                    DiagnosticSuggestionDto dto = new DiagnosticSuggestionDto();
                    dto.setDiseaseName(s.getDiseaseName());
                    dto.setProbability(s.getProbability());
                    dto.setDescription(s.getDescription());
                    return dto;
                }).collect(Collectors.toList())
        );

        return response;
    }

    public AnalysisResponseDto getAnalysis(Long id) {
        MedicalAnalysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse non trouvée"));
        return createAnalysisResponseDto(analysis);
    }

    private AnalysisResponseDto createAnalysisResponseDto(MedicalAnalysis analysis) {
        AnalysisResponseDto response = new AnalysisResponseDto();
        response.setAnalysisId(analysis.getId());
        response.setPatientName(analysis.getPatient().getFirstName() + " " + analysis.getPatient().getLastName());
        response.setDate(analysis.getCreatedAt().toString());
        response.setSuggestions(
                analysis.getDiagnosticSuggestions().stream().map(s -> {
                    DiagnosticSuggestionDto dto = new DiagnosticSuggestionDto();
                    dto.setDiseaseName(s.getDiseaseName());
                    dto.setProbability(s.getProbability());
                    dto.setDescription(s.getDescription());
                    return dto;
                }).collect(Collectors.toList())
        );
        return response;
    }
}
