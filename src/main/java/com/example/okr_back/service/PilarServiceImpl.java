package com.example.okr_back.service;

import com.example.okr_back.dto.PilarDto;
import com.example.okr_back.entities.Pilar;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.persistence.PilarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PilarServiceImpl implements IPilarService {

    private final PilarRepository pilarRepository;

    public PilarServiceImpl(PilarRepository pilarRepository) {
        this.pilarRepository = pilarRepository;
    }

    @Override
    public PilarDto createPilar(PilarDto pilarDto) {
        log.info("Creating new pilar with name: {}", pilarDto.getName());
        if (pilarRepository.existsByName(pilarDto.getName())) {
            throw new ResourceAlreadyExistsException("Pilar with name '" + pilarDto.getName() + "' already exists.");
        }
        Pilar pilar = new Pilar();
        pilar.setName(pilarDto.getName());
        pilar.setActive(true);
        Pilar savedPilar = pilarRepository.save(pilar);
        log.info("Saved new pilar with id: {}", savedPilar.getId());
        pilarDto.setId(savedPilar.getId());
        return pilarDto;
    }

    @Override
    public List<PilarDto> getAllPilares() {
        log.info("Fetching all pilares");
        return pilarRepository.findAll().stream()
                .map(pilar -> new PilarDto(pilar.getId(), pilar.getName()))
                .collect(Collectors.toList());
    }
}
