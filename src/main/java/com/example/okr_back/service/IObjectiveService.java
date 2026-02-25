package com.example.okr_back.service;

import com.example.okr_back.dto.ObjectiveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IObjectiveService {
    ObjectiveDto createObjective(ObjectiveDto objectiveDto);
    Page<ObjectiveDto> getAllObjectives(Pageable pageable);
    ObjectiveDto updateObjective(Long id, ObjectiveDto objectiveDto);
    void deleteObjective(Long id);
}
