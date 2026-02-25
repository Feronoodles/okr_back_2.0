package com.example.okr_back.controller;

import com.example.okr_back.dto.KeyResultDto;
import com.example.okr_back.service.IKeyResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/keyresults")
@SecurityRequirement(name = "bearerAuth")
public class KeyResultController {

    private final IKeyResultService keyResultService;

    public KeyResultController(IKeyResultService keyResultService) {
        this.keyResultService = keyResultService;
    }

    @Operation(summary = "Create a new Key Result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Key Result created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Objective not found")
    })
    @PostMapping
    public ResponseEntity<KeyResultDto> createKeyResult(@Valid @RequestBody KeyResultDto keyResultDto) {
        KeyResultDto createdKeyResult = keyResultService.createKeyResult(keyResultDto);
        return new ResponseEntity<>(createdKeyResult, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing Key Result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Key Result updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Key Result not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<KeyResultDto> updateKeyResult(@PathVariable Long id, @Valid @RequestBody KeyResultDto keyResultDto) {
        KeyResultDto updatedKeyResult = keyResultService.updateKeyResult(id, keyResultDto);
        return ResponseEntity.ok(updatedKeyResult);
    }

    @Operation(summary = "Soft delete a Key Result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Key Result deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Key Result not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyResult(@PathVariable Long id) {
        keyResultService.deleteKeyResult(id);
        return ResponseEntity.noContent().build();
    }
}
