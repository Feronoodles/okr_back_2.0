package com.example.okr_back.persistence;

import com.example.okr_back.entities.Objective;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Override
    @EntityGraph(value = "Objective.withKeyResults")
    Page<Objective> findAll(Pageable pageable);

    boolean existsByDescription(String description);
}
