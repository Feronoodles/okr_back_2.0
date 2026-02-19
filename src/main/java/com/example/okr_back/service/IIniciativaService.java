package com.example.okr_back.service;

import com.example.okr_back.dto.IniciativaDto;

import java.util.List;

public interface IIniciativaService {
    IniciativaDto createIniciativa(IniciativaDto iniciativaDto);
    List<IniciativaDto> getAllIniciativas();
}
