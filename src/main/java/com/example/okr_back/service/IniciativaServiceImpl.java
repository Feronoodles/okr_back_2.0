package com.example.okr_back.service;

import com.example.okr_back.dto.IniciativaDto;
import com.example.okr_back.entities.Iniciativa;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.persistence.IniciativaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IniciativaServiceImpl implements IIniciativaService {

    private final IniciativaRepository iniciativaRepository;

    public IniciativaServiceImpl(IniciativaRepository iniciativaRepository) {
        this.iniciativaRepository = iniciativaRepository;
    }

    @Override
    public IniciativaDto createIniciativa(IniciativaDto iniciativaDto) {
        log.info("Creating new iniciativa with name: {}", iniciativaDto.getName());
        if (iniciativaRepository.existsByName(iniciativaDto.getName())) {
            throw new ResourceAlreadyExistsException("Iniciativa with name '" + iniciativaDto.getName() + "' already exists.");
        }
        Iniciativa iniciativa = new Iniciativa();
        iniciativa.setName(iniciativaDto.getName());
        iniciativa.setActive(true);
        Iniciativa savedIniciativa = iniciativaRepository.save(iniciativa);
        log.info("Saved new iniciativa with id: {}", savedIniciativa.getId());
        iniciativaDto.setId(savedIniciativa.getId());
        return iniciativaDto;
    }

    @Override
    public List<IniciativaDto> getAllIniciativas() {
        log.info("Fetching all iniciativas");
        return iniciativaRepository.findAll().stream()
                .map(iniciativa -> new IniciativaDto(iniciativa.getId(), iniciativa.getName()))
                .collect(Collectors.toList());
    }
}
