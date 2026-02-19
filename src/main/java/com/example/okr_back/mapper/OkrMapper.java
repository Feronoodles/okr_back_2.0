package com.example.okr_back.mapper;

import com.example.okr_back.dto.KeyResultDto;
import com.example.okr_back.dto.ObjectiveDto;
import com.example.okr_back.entities.KeyResult;
import com.example.okr_back.entities.Objective;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OkrMapper {

    public ObjectiveDto toObjectiveDto(Objective objective) {
        ObjectiveDto dto = new ObjectiveDto();
        dto.setId(objective.getId());
        dto.setPeriodId(objective.getPeriod().getId());
        dto.setPilarId(objective.getPilar().getId());
        dto.setIniciativaId(objective.getIniciativa().getId());
        dto.setDescription(objective.getDescription());
        if (objective.getKeyResults() != null) {
            dto.setKeyResults(objective.getKeyResults().stream()
                    .map(this::toKeyResultDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public KeyResultDto toKeyResultDto(KeyResult keyResult) {
        KeyResultDto dto = new KeyResultDto();
        dto.setAreaId(keyResult.getArea().getId());
        dto.setOwnerId(keyResult.getOwner().getId());
        dto.setCode(keyResult.getCode());
        dto.setDescription(keyResult.getDescription());
        dto.setMetricName(keyResult.getMetricName());
        dto.setDataType(keyResult.getDataType());
        dto.setBaselineValue(keyResult.getBaselineValue());
        dto.setTargetValue(keyResult.getTargetValue());
        dto.setCurrentValue(keyResult.getCurrentValue());
        dto.setStatus(keyResult.getStatus());
        dto.setNotesBlockers(keyResult.getNotesBlockers());
        return dto;
    }
}
