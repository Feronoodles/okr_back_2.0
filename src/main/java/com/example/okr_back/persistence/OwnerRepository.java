package com.example.okr_back.persistence;

import com.example.okr_back.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByName(String name);
}
