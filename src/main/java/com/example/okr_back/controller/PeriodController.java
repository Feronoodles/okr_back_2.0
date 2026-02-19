package com.example.okr_back.controller;

import com.example.okr_back.dto.PeriodDto;
import com.example.okr_back.service.IPeriodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/periods")
@SecurityRequirement(name = "bearerAuth")
public class PeriodController {

    private final IPeriodService periodService;

    public PeriodController(IPeriodService periodService) {
        this.periodService = periodService;
    }

    @Operation(summary = "Create a new period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Period created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<PeriodDto> createPeriod(@Valid @RequestBody PeriodDto periodDto) {
        PeriodDto createdPeriod = periodService.createPeriod(periodDto);
        return new ResponseEntity<>(createdPeriod, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all periods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Periods retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<PeriodDto>> getAllPeriods() {
        return ResponseEntity.ok(periodService.getAllPeriods());
    }
}
