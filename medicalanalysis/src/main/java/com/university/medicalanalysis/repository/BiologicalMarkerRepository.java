package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.BiologicalMarker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiologicalMarkerRepository extends JpaRepository<BiologicalMarker, Long> {
}
