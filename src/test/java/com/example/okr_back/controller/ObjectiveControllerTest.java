package com.example.okr_back.controller;

import com.example.okr_back.config.SecurityConfig;
import com.example.okr_back.dto.ObjectiveDto;
import com.example.okr_back.infra.security.JwtAuthenticationFilter;
import com.example.okr_back.service.IObjectiveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ObjectiveController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
@AutoConfigureMockMvc(addFilters = false)
class ObjectiveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IObjectiveService objectiveService;

    @Autowired
    private ObjectMapper objectMapper;

    private ObjectiveDto objectiveDto;

    @BeforeEach
    void setUp() {
        objectiveDto = new ObjectiveDto();
        objectiveDto.setId(1L);
        objectiveDto.setPeriodId(1L);
        objectiveDto.setPilarId(1L);
        objectiveDto.setIniciativaId(1L);
        objectiveDto.setDescription("Test Objective");
    }

    @Test
    void createObjective_ReturnsCreated() throws Exception {
        when(objectiveService.createObjective(any(ObjectiveDto.class))).thenReturn(objectiveDto);

        mockMvc.perform(post("/objectives")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objectiveDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Test Objective"));
    }

    @Test
    void getAllObjectives_ReturnsPage() throws Exception {
        Page<ObjectiveDto> page = new PageImpl<>(Collections.singletonList(objectiveDto));
        when(objectiveService.getAllObjectives(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/objectives")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].description").value("Test Objective"));
    }
}
