package com.university.medicalanalysis.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class BiologicalMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String unit;
    private Double minNormalValue;
    private Double maxNormalValue;

    @OneToMany(mappedBy = "marker", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BiologicalResult> results;
}
