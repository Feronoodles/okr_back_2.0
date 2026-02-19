package com.example.okr_back.service;

import com.example.okr_back.dto.AreaDto;
import com.example.okr_back.entities.Area;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.persistence.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AreaServiceImpl implements IAreaService {

    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public AreaDto createArea(AreaDto areaDto) {
        log.info("Creating new area with name: {}", areaDto.getName());
        if (areaRepository.existsByName(areaDto.getName())) {
            throw new ResourceAlreadyExistsException("Area with name '" + areaDto.getName() + "' already exists.");
        }
        Area area = new Area();
        area.setName(areaDto.getName());
        area.setActive(true);
        Area savedArea = areaRepository.save(area);
        log.info("Saved new area with id: {}", savedArea.getId());
        areaDto.setId(savedArea.getId());
        return areaDto;
    }

    @Override
    public List<AreaDto> getAllAreas() {
        log.info("Fetching all areas");
        return areaRepository.findAll().stream()
                .map(area -> new AreaDto(area.getId(), area.getName()))
                .collect(Collectors.toList());
    }
}
