package com.example.okr_back.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "key_results")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private Objective objective;

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String metricName;

    @Enumerated(EnumType.STRING)
    private DataType dataType;

    private BigDecimal baselineValue;

    private BigDecimal targetValue;

    private BigDecimal currentValue;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate lastUpdated;

    @Column(columnDefinition = "TEXT")
    private String notesBlockers;

    @Column(nullable = false)
    private Boolean active = true;
}
