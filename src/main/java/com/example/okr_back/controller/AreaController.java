package com.example.okr_back.controller;

import com.example.okr_back.dto.AreaDto;
import com.example.okr_back.service.IAreaService;
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
@RequestMapping("/areas")
@SecurityRequirement(name = "bearerAuth")
public class AreaController {

    private final IAreaService areaService;

    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @Operation(summary = "Create a new area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Area created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<AreaDto> createArea(@Valid @RequestBody AreaDto areaDto) {
        AreaDto createdArea = areaService.createArea(areaDto);
        return new ResponseEntity<>(createdArea, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all areas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Areas retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<AreaDto>> getAllAreas() {
        return ResponseEntity.ok(areaService.getAllAreas());
    }
}
