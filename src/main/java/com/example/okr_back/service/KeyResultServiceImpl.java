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
import java.time.LocalDateTime;

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
    public KeyResultDto createKeyResult(KeyResultDto keyResultDto) {
        log.info("Creating new Key Result for objective id: {}", keyResultDto.getObjectiveId());

        Objective objective = objectiveRepository.findById(keyResultDto.getObjectiveId())
                .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + keyResultDto.getObjectiveId()));
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
        keyResult.setNotesBlockers(keyResultDto.getNotesBlockers());
        keyResult.setActive(true);

        KeyResult savedKeyResult = keyResultRepository.save(keyResult);
        log.info("Saved new key result with code: {}", savedKeyResult.getCode());

        return okrMapper.toKeyResultDto(savedKeyResult);
    }

    @Override
    @Transactional(readOnly = true)
    public KeyResultDto getKeyResultById(Long id) {
        log.info("Fetching active Key Result with id: {}", id);
        KeyResult keyResult = keyResultRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Key Result not found or inactive with id: " + id));
        return okrMapper.toKeyResultDto(keyResult);
    }

    @Override
    @Transactional
    public KeyResultDto updateKeyResult(Long id, KeyResultDto keyResultDto) {
        log.info("Updating Key Result with id: {}", id);

        KeyResult keyResult = keyResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + id));

        if (keyResultDto.getObjectiveId() != null) {
            Objective objective = objectiveRepository.findById(keyResultDto.getObjectiveId())
                    .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + keyResultDto.getObjectiveId()));
            keyResult.setObjective(objective);
        }

        if (keyResultDto.getAreaId() != null) {
            Area area = areaRepository.findById(keyResultDto.getAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Area not found with id: " + keyResultDto.getAreaId()));
            keyResult.setArea(area);
        }

        if (keyResultDto.getOwnerId() != null) {
            Owner owner = ownerRepository.findById(keyResultDto.getOwnerId())
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + keyResultDto.getOwnerId()));
            keyResult.setOwner(owner);
        }

        if (keyResultDto.getCode() != null) {
            keyResult.setCode(keyResultDto.getCode());
        }
        if (keyResultDto.getDescription() != null) {
            keyResult.setDescription(keyResultDto.getDescription());
        }
        if (keyResultDto.getMetricName() != null) {
            keyResult.setMetricName(keyResultDto.getMetricName());
        }
        if (keyResultDto.getDataType() != null) {
            keyResult.setDataType(keyResultDto.getDataType());
        }
        if (keyResultDto.getBaselineValue() != null) {
            keyResult.setBaselineValue(keyResultDto.getBaselineValue());
        }
        if (keyResultDto.getTargetValue() != null) {
            keyResult.setTargetValue(keyResultDto.getTargetValue());
        }
        if (keyResultDto.getCurrentValue() != null) {
            keyResult.setCurrentValue(keyResultDto.getCurrentValue());
        }
        if (keyResultDto.getStatus() != null) {
            keyResult.setStatus(keyResultDto.getStatus());
        }
        if (keyResultDto.getNotesBlockers() != null) {
            keyResult.setNotesBlockers(keyResultDto.getNotesBlockers());
        }

        KeyResult updatedKeyResult = keyResultRepository.save(keyResult);
        log.info("Updated key result with id: {}", updatedKeyResult.getId());

        return okrMapper.toKeyResultDto(updatedKeyResult);
    }

    @Override
    @Transactional
    public void deleteKeyResult(Long id) {
        log.info("Soft deleting Key Result with id: {}", id);
        KeyResult keyResult = keyResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Key Result not found with id: " + id));
        
        keyResult.setActive(false);
        keyResultRepository.save(keyResult);
        log.info("Key Result with id: {} soft deleted", id);
    }
}
