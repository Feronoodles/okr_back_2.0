package com.example.okr_back.persistence;

import com.example.okr_back.entities.Pilar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilarRepository extends JpaRepository<Pilar, Long> {
    boolean existsByName(String name);
}
