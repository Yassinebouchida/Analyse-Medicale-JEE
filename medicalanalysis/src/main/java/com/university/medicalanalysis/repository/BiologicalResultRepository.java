// BiologicalResultRepository.java
package com.university.medicalanalysis.repository;

import com.university.medicalanalysis.model.BiologicalResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiologicalResultRepository extends JpaRepository<BiologicalResult, Long> {
}
