package com.example.okr_back.controller;

import com.example.okr_back.dto.PilarDto;
import com.example.okr_back.service.IPilarService;
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
@RequestMapping("/pilares")
@SecurityRequirement(name = "bearerAuth")
public class PilarController {

    private final IPilarService pilarService;

    public PilarController(IPilarService pilarService) {
        this.pilarService = pilarService;
    }

    @Operation(summary = "Create a new pilar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pilar created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<PilarDto> createPilar(@Valid @RequestBody PilarDto pilarDto) {
        PilarDto createdPilar = pilarService.createPilar(pilarDto);
        return new ResponseEntity<>(createdPilar, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all pilares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pilares retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<PilarDto>> getAllPilares() {
        return ResponseEntity.ok(pilarService.getAllPilares());
    }
}
