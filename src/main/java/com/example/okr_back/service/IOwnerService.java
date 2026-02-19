package com.example.okr_back.service;

import com.example.okr_back.dto.OwnerDto;

import java.util.List;

public interface IOwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);
    List<OwnerDto> getAllOwners();
}
