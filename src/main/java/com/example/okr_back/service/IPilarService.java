package com.example.okr_back.service;

import com.example.okr_back.dto.PilarDto;

import java.util.List;

public interface IPilarService {
    PilarDto createPilar(PilarDto pilarDto);
    List<PilarDto> getAllPilares();
}
