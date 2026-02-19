package com.example.okr_back.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectiveDto {

    private Long id;

    @NotNull
    private Long periodId;

    @NotNull
    private Long pilarId;

    @NotNull
    private Long iniciativaId;

    @NotBlank
    private String description;

    @Valid
    private List<KeyResultDto> keyResults;
}
