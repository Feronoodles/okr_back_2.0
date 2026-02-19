package com.example.okr_back.service;

import com.example.okr_back.dto.PeriodDto;
import com.example.okr_back.entities.Period;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.persistence.PeriodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PeriodServiceImpl implements IPeriodService {

    private final PeriodRepository periodRepository;

    public PeriodServiceImpl(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    @Override
    public PeriodDto createPeriod(PeriodDto periodDto) {
        log.info("Creating new period with name: {}", periodDto.getName());
        if (periodRepository.existsByName(periodDto.getName())) {
            throw new ResourceAlreadyExistsException("Period with name '" + periodDto.getName() + "' already exists.");
        }
        Period period = new Period();
        period.setName(periodDto.getName());
        period.setActive(true);
        Period savedPeriod = periodRepository.save(period);
        log.info("Saved new period with id: {}", savedPeriod.getId());
        periodDto.setId(savedPeriod.getId());
        return periodDto;
    }

    @Override
    public List<PeriodDto> getAllPeriods() {
        log.info("Fetching all periods");
        return periodRepository.findAll().stream()
                .map(period -> new PeriodDto(period.getId(), period.getName()))
                .collect(Collectors.toList());
    }
}
