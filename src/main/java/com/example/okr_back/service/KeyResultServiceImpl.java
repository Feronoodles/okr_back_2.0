package com.example.okr_back.service;

import com.example.okr_back.dto.KeyResultDto;
import com.example.okr_back.entities.Area;
import com.example.okr_back.entities.KeyResult;
import com.example.okr_back.entities.Objective;
import com.example.okr_back.entities.Owner;
import com.example.okr_back.mapper.OkrMapper;
import com.example.okr_back.persistence.AreaRepository;
import com.example.okr_back.persistence.KeyResultRepository;
import com.example.okr_back.persistence.ObjectiveRepository;
import com.example.okr_back.persistence.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@Slf4j
public class KeyResultServiceImpl implements IKeyResultService {

    private final KeyResultRepository keyResultRepository;
    private final ObjectiveRepository objectiveRepository;
    private final AreaRepository areaRepository;
    private final OwnerRepository ownerRepository;
    private final OkrMapper okrMapper;

    public KeyResultServiceImpl(KeyResultRepository keyResultRepository, ObjectiveRepository objectiveRepository, AreaRepository areaRepository, OwnerRepository ownerRepository, OkrMapper okrMapper) {
        this.keyResultRepository = keyResultRepository;
        this.objectiveRepository = objectiveRepository;
        this.areaRepository = areaRepository;
        this.ownerRepository = ownerRepository;
        this.okrMapper = okrMapper;
    }

    @Override
    @Transactional
    public KeyResultDto createKeyResult(KeyResultDto keyResultDto, Long objectiveId) {
        log.info("Creating new Key Result for objective id: {}", objectiveId);

        Objective objective = objectiveRepository.findById(objectiveId)
                .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + objectiveId));
        Area area = areaRepository.findById(keyResultDto.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Area not found with id: " + keyResultDto.getAreaId()));
        Owner owner = ownerRepository.findById(keyResultDto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + keyResultDto.getOwnerId()));

        KeyResult keyResult = new KeyResult();
        keyResult.setObjective(objective);
        keyResult.setArea(area);
        keyResult.setOwner(owner);
        keyResult.setCode(keyResultDto.getCode());
        keyResult.setDescription(keyResultDto.getDescription());
        keyResult.setMetricName(keyResultDto.getMetricName());
        keyResult.setDataType(keyResultDto.getDataType());
        keyResult.setBaselineValue(keyResultDto.getBaselineValue());
        keyResult.setTargetValue(keyResultDto.getTargetValue());
        keyResult.setCurrentValue(keyResultDto.getCurrentValue());
        keyResult.setStatus(keyResultDto.getStatus());
        keyResult.setLastUpdated(LocalDate.now());
        keyResult.setNotesBlockers(keyResultDto.getNotesBlockers());
        keyResult.setActive(true);

        KeyResult savedKeyResult = keyResultRepository.save(keyResult);
        log.info("Saved new key result with code: {}", savedKeyResult.getCode());

        return okrMapper.toKeyResultDto(savedKeyResult);
    }
}
