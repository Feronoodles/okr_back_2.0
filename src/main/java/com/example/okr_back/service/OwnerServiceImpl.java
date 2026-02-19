package com.example.okr_back.service;

import com.example.okr_back.dto.OwnerDto;
import com.example.okr_back.entities.Area;
import com.example.okr_back.entities.Owner;
import com.example.okr_back.infra.exceptions.ResourceAlreadyExistsException;
import com.example.okr_back.persistence.AreaRepository;
import com.example.okr_back.persistence.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements IOwnerService {

    private final OwnerRepository ownerRepository;
    private final AreaRepository areaRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, AreaRepository areaRepository) {
        this.ownerRepository = ownerRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        log.info("Creating new owner with name: {}", ownerDto.getFullName());
        if (ownerRepository.existsByFullName(ownerDto.getFullName())) {
            throw new ResourceAlreadyExistsException("Owner with name '" + ownerDto.getFullName() + "' already exists.");
        }

        Owner owner = new Owner();
        owner.setFullName(ownerDto.getFullName());
        owner.setEmail(ownerDto.getEmail());
        owner.setActive(true);

        if (ownerDto.getAreaId() != null) {
            Area area = areaRepository.findById(ownerDto.getAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Area not found with id: " + ownerDto.getAreaId()));
            owner.setArea(area);
        }

        Owner savedOwner = ownerRepository.save(owner);
        log.info("Saved new owner with id: {}", savedOwner.getId());
        ownerDto.setId(savedOwner.getId());
        return ownerDto;
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        log.info("Fetching all owners");
        return ownerRepository.findAll().stream()
                .map(owner -> new OwnerDto(
                        owner.getId(),
                        owner.getFullName(),
                        owner.getEmail(),
                        owner.getArea() != null ? owner.getArea().getId() : null
                ))
                .collect(Collectors.toList());
    }
}
