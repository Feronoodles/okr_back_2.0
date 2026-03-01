package com.example.okr_back.service;

import com.example.okr_back.dto.ObjectiveDto;
import com.example.okr_back.entities.Iniciativa;
import com.example.okr_back.entities.Objective;
import com.example.okr_back.entities.Period;
import com.example.okr_back.entities.Pilar;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.mapper.OkrMapper;
import com.example.okr_back.persistence.IniciativaRepository;
import com.example.okr_back.persistence.ObjectiveRepository;
import com.example.okr_back.persistence.PeriodRepository;
import com.example.okr_back.persistence.PilarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class ObjectiveServiceImpl implements IObjectiveService {

    private final ObjectiveRepository objectiveRepository;
    private final PeriodRepository periodRepository;
    private final PilarRepository pilarRepository;
    private final IniciativaRepository iniciativaRepository;
    private final OkrMapper okrMapper;

    public ObjectiveServiceImpl(ObjectiveRepository objectiveRepository, PeriodRepository periodRepository, PilarRepository pilarRepository, IniciativaRepository iniciativaRepository, OkrMapper okrMapper) {
        this.objectiveRepository = objectiveRepository;
        this.periodRepository = periodRepository;
        this.pilarRepository = pilarRepository;
        this.iniciativaRepository = iniciativaRepository;
        this.okrMapper = okrMapper;
    }

    @Override
    @Transactional
    public ObjectiveDto createObjective(ObjectiveDto objectiveDto) {
        log.info("Creating new Objective");

        if (objectiveRepository.existsByDescriptionAndActiveTrue(objectiveDto.getDescription())) {
            throw new ResourceAlreadyExistsException("Objective with description '" + objectiveDto.getDescription() + "' already exists.");
        }

        Period period = periodRepository.findById(objectiveDto.getPeriodId())
                .orElseThrow(() -> new EntityNotFoundException("Period not found with id: " + objectiveDto.getPeriodId()));
        Pilar pilar = pilarRepository.findById(objectiveDto.getPilarId())
                .orElseThrow(() -> new EntityNotFoundException("Pilar not found with id: " + objectiveDto.getPilarId()));
        Iniciativa iniciativa = iniciativaRepository.findById(objectiveDto.getIniciativaId())
                .orElseThrow(() -> new EntityNotFoundException("Iniciativa not found with id: " + objectiveDto.getIniciativaId()));

        Objective objective = new Objective();
        objective.setPeriod(period);
        objective.setPilar(pilar);
        objective.setIniciativa(iniciativa);
        objective.setDescription(objectiveDto.getDescription());
        objective.setActive(true);

        Objective savedObjective = objectiveRepository.save(objective);
        log.info("Saved new objective with id: {}", savedObjective.getId());

        return okrMapper.toObjectiveDto(savedObjective);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectiveDto> getAllObjectives(Pageable pageable) {
        log.info("Fetching all active Objectives with pagination");
        return objectiveRepository.findByActiveTrue(pageable)
                .map(okrMapper::toObjectiveDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectiveDto getObjectiveById(Long id) {
        log.info("Fetching active Objective with id: {}", id);
        Objective objective = objectiveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Objective not found or inactive with id: " + id));
        return okrMapper.toObjectiveDto(objective);
    }

    @Override
    @Transactional
    public ObjectiveDto updateObjective(Long id, ObjectiveDto objectiveDto) {
        log.info("Updating Objective with id: {}", id);

        Objective objective = objectiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + id));

        if (objectiveDto.getDescription() != null && !objective.getDescription().equals(objectiveDto.getDescription())) {
            if (objectiveRepository.existsByDescriptionAndActiveTrue(objectiveDto.getDescription())) {
                throw new ResourceAlreadyExistsException("Objective with description '" + objectiveDto.getDescription() + "' already exists.");
            }
            objective.setDescription(objectiveDto.getDescription());
        }

        if (objectiveDto.getPeriodId() != null) {
            Period period = periodRepository.findById(objectiveDto.getPeriodId())
                    .orElseThrow(() -> new EntityNotFoundException("Period not found with id: " + objectiveDto.getPeriodId()));
            objective.setPeriod(period);
        }

        if (objectiveDto.getPilarId() != null) {
            Pilar pilar = pilarRepository.findById(objectiveDto.getPilarId())
                    .orElseThrow(() -> new EntityNotFoundException("Pilar not found with id: " + objectiveDto.getPilarId()));
            objective.setPilar(pilar);
        }

        if (objectiveDto.getIniciativaId() != null) {
            Iniciativa iniciativa = iniciativaRepository.findById(objectiveDto.getIniciativaId())
                    .orElseThrow(() -> new EntityNotFoundException("Iniciativa not found with id: " + objectiveDto.getIniciativaId()));
            objective.setIniciativa(iniciativa);
        }

        Objective updatedObjective = objectiveRepository.save(objective);
        log.info("Updated objective with id: {}", updatedObjective.getId());

        return okrMapper.toObjectiveDto(updatedObjective);
    }

    @Override
    @Transactional
    public void deleteObjective(Long id) {
        log.info("Soft deleting Objective with id: {}", id);
        Objective objective = objectiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objective not found with id: " + id));
        
        objective.setActive(false);
        
        if (objective.getKeyResults() != null) {
            objective.getKeyResults().forEach(kr -> kr.setActive(false));
        }
        
        objectiveRepository.save(objective);
        log.info("Objective with id: {} and its Key Results soft deleted", id);
    }
}
