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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeyResultServiceImplTest {

    @Mock
    private KeyResultRepository keyResultRepository;
    @Mock
    private ObjectiveRepository objectiveRepository;
    @Mock
    private AreaRepository areaRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private OkrMapper okrMapper;

    @InjectMocks
    private KeyResultServiceImpl keyResultService;

    private KeyResultDto keyResultDto;
    private Objective objective;
    private Area area;
    private Owner owner;
    private KeyResult keyResult;

    @BeforeEach
    void setUp() {
        keyResultDto = new KeyResultDto();
        keyResultDto.setAreaId(1L);
        keyResultDto.setOwnerId(1L);
        keyResultDto.setCode("KR1");
        keyResultDto.setDescription("Test KR");
        keyResultDto.setBaselineValue(BigDecimal.ZERO);
        keyResultDto.setTargetValue(BigDecimal.TEN);
        keyResultDto.setCurrentValue(BigDecimal.ONE);

        objective = new Objective();
        objective.setId(1L);

        area = new Area();
        area.setId(1L);

        owner = new Owner();
        owner.setId(1L);

        keyResult = new KeyResult();
        keyResult.setId(1L);
        keyResult.setCode("KR1");
    }

    @Test
    void createKeyResult_Success() {
        when(objectiveRepository.findById(1L)).thenReturn(Optional.of(objective));
        when(areaRepository.findById(1L)).thenReturn(Optional.of(area));
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(keyResultRepository.save(any(KeyResult.class))).thenReturn(keyResult);
        when(okrMapper.toKeyResultDto(any(KeyResult.class))).thenReturn(keyResultDto);

        KeyResultDto result = keyResultService.createKeyResult(keyResultDto, 1L);

        assertNotNull(result);
        assertEquals("KR1", result.getCode());
        verify(keyResultRepository, times(1)).save(any(KeyResult.class));
    }

    @Test
    void createKeyResult_ObjectiveNotFound_ThrowsException() {
        when(objectiveRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> keyResultService.createKeyResult(keyResultDto, 1L));
        verify(keyResultRepository, never()).save(any(KeyResult.class));
    }
}
