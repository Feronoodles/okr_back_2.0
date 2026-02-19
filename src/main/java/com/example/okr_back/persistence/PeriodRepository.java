package com.example.okr_back.persistence;

import com.example.okr_back.entities.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    boolean existsByName(String name);
}
