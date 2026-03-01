package com.example.okr_back.persistence;

import com.example.okr_back.entities.Objective;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @EntityGraph(value = "Objective.withKeyResults")
    Page<Objective> findByActiveTrue(Pageable pageable);

    @EntityGraph(value = "Objective.withKeyResults")
    Optional<Objective> findByIdAndActiveTrue(Long id);

    boolean existsByDescriptionAndActiveTrue(String description);
}
