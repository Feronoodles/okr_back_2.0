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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObjectiveServiceImplTest {

    @Mock
    private ObjectiveRepository objectiveRepository;
    @Mock
    private PeriodRepository periodRepository;
    @Mock
    private PilarRepository pilarRepository;
    @Mock
    private IniciativaRepository iniciativaRepository;
    @Mock
    private OkrMapper okrMapper;

    @InjectMocks
    private ObjectiveServiceImpl objectiveService;

    private ObjectiveDto objectiveDto;
    private Period period;
    private Pilar pilar;
    private Iniciativa iniciativa;
    private Objective objective;

    @BeforeEach
    void setUp() {
        objectiveDto = new ObjectiveDto();
        objectiveDto.setPeriodId(1L);
        objectiveDto.setPilarId(1L);
        objectiveDto.setIniciativaId(1L);
        objectiveDto.setDescription("Test Objective");

        period = new Period();
        period.setId(1L);
        period.setName("Q1 2026");

        pilar = new Pilar();
        pilar.setId(1L);
        pilar.setName("Growth");

        iniciativa = new Iniciativa();
        iniciativa.setId(1L);
        iniciativa.setName("Project X");

        objective = new Objective();
        objective.setId(1L);
        objective.setPeriod(period);
        objective.setPilar(pilar);
        objective.setIniciativa(iniciativa);
        objective.setDescription("Test Objective");
    }

    @Test
    void createObjective_Success() {
        when(objectiveRepository.existsByDescription(anyString())).thenReturn(false);
        when(periodRepository.findById(1L)).thenReturn(Optional.of(period));
        when(pilarRepository.findById(1L)).thenReturn(Optional.of(pilar));
        when(iniciativaRepository.findById(1L)).thenReturn(Optional.of(iniciativa));
        when(objectiveRepository.save(any(Objective.class))).thenReturn(objective);
        when(okrMapper.toObjectiveDto(any(Objective.class))).thenReturn(objectiveDto);

        ObjectiveDto result = objectiveService.createObjective(objectiveDto);

        assertNotNull(result);
        assertEquals("Test Objective", result.getDescription());
        verify(objectiveRepository, times(1)).save(any(Objective.class));
    }

    @Test
    void createObjective_DescriptionExists_ThrowsException() {
        when(objectiveRepository.existsByDescription(anyString())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> objectiveService.createObjective(objectiveDto));
        verify(objectiveRepository, never()).save(any(Objective.class));
    }

    @Test
    void createObjective_PeriodNotFound_ThrowsException() {
        when(objectiveRepository.existsByDescription(anyString())).thenReturn(false);
        when(periodRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> objectiveService.createObjective(objectiveDto));
    }
}
