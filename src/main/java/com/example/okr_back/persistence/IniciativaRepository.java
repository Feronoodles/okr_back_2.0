package com.example.okr_back.persistence;

import com.example.okr_back.entities.Iniciativa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IniciativaRepository extends JpaRepository<Iniciativa, Long> {
    boolean existsByName(String name);
}
