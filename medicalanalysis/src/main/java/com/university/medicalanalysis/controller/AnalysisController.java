package com.university.medicalanalysis.controller;

import com.university.medicalanalysis.dto.AnalysisRequestDto;
import com.university.medicalanalysis.dto.AnalysisResponseDto;
import com.university.medicalanalysis.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AnalysisController {

    private final AnalysisService analysisService;

    /* POST /api/analyses -------------------------------------------------- */
    @PostMapping
    public AnalysisResponseDto create(@RequestBody AnalysisRequestDto dto) {
        return analysisService.createAnalysis(dto);
    }

    /* GET /api/analyses/{id} --------------------------------------------- */
    @GetMapping("/{id}")
    public AnalysisResponseDto get(@PathVariable Long id) {
        return analysisService.getAnalysis(id);
    }
}

