package com.example.okr_back.service;

import com.example.okr_back.dto.AreaDto;

import java.util.List;

public interface IAreaService {
    AreaDto createArea(AreaDto areaDto);
    List<AreaDto> getAllAreas();
}
