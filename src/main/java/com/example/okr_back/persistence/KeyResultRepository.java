package com.example.okr_back.persistence;

import com.example.okr_back.entities.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyResultRepository extends JpaRepository<KeyResult, Long> {
    Optional<KeyResult> findByIdAndActiveTrue(Long id);
}
