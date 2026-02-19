package com.example.okr_back.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "objectives")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "Objective.withKeyResults",
        attributeNodes = @NamedAttributeNode("keyResults")
)
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @ManyToOne
    @JoinColumn(name = "pilar_id", nullable = false)
    private Pilar pilar;

    @ManyToOne
    @JoinColumn(name = "iniciativa_id", nullable = false)
    private Iniciativa iniciativa;

    @Column(columnDefinition = "TEXT", unique = true)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KeyResult> keyResults;
}
