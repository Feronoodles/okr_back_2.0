package com.example.okr_back.service;

import com.example.okr_back.dto.KeyResultDto;

public interface IKeyResultService {
    KeyResultDto createKeyResult(KeyResultDto keyResultDto, Long objectiveId);
}
