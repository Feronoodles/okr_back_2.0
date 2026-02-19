package com.example.okr_back.controller;

import com.example.okr_back.dto.ObjectiveDto;
import com.example.okr_back.service.IObjectiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/objectives")
public class ObjectiveController {

    private final IObjectiveService objectiveService;

    public ObjectiveController(IObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @Operation(summary = "Create a new Objective")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Objective created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ObjectiveDto> createObjective(@Valid @RequestBody ObjectiveDto objectiveDto) {
        ObjectiveDto createdObjective = objectiveService.createObjective(objectiveDto);
        return new ResponseEntity<>(createdObjective, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Objectives with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objectives retrieved successfully"),
    })
    @GetMapping
    public ResponseEntity<Page<ObjectiveDto>> getAllObjectives(Pageable pageable) {
        return ResponseEntity.ok(objectiveService.getAllObjectives(pageable));
    }
}
