package com.example.okr_back.controller;

import com.example.okr_back.dto.IniciativaDto;
import com.example.okr_back.service.IIniciativaService;
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
@RequestMapping("/iniciativas")
@SecurityRequirement(name = "bearerAuth")
public class IniciativaController {

    private final IIniciativaService iniciativaService;

    public IniciativaController(IIniciativaService iniciativaService) {
        this.iniciativaService = iniciativaService;
    }

    @Operation(summary = "Create a new iniciativa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Iniciativa created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<IniciativaDto> createIniciativa(@Valid @RequestBody IniciativaDto iniciativaDto) {
        IniciativaDto createdIniciativa = iniciativaService.createIniciativa(iniciativaDto);
        return new ResponseEntity<>(createdIniciativa, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all iniciativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Iniciativas retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<IniciativaDto>> getAllIniciativas() {
        return ResponseEntity.ok(iniciativaService.getAllIniciativas());
    }
}
