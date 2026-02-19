package com.example.okr_back.service;

import com.example.okr_back.dto.PeriodDto;

import java.util.List;

public interface IPeriodService {
    PeriodDto createPeriod(PeriodDto periodDto);
    List<PeriodDto> getAllPeriods();
}
