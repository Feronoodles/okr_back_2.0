package com.example.okr_back.dto;

import com.example.okr_back.entities.DataType;
import com.example.okr_back.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyResultDto {

    @NotNull
    private Long areaId;

    @NotNull
    private Long ownerId;

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotBlank
    private String metricName;

    @NotNull
    private DataType dataType;

    @NotNull
    private BigDecimal baselineValue;

    @NotNull
    private BigDecimal targetValue;

    @NotNull
    private BigDecimal currentValue;

    @NotNull
    private Status status;

    private String notesBlockers;
}
