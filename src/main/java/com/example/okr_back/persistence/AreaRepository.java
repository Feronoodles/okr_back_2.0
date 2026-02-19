package com.example.okr_back.persistence;

import com.example.okr_back.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    boolean existsByName(String name);
}
