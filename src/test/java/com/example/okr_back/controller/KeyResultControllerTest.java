package com.example.okr_back.controller;

import com.example.okr_back.config.SecurityConfig;
import com.example.okr_back.dto.KeyResultDto;
import com.example.okr_back.entities.DataType;
import com.example.okr_back.entities.Status;
import com.example.okr_back.infra.security.JwtAuthenticationFilter;
import com.example.okr_back.service.IKeyResultService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KeyResultController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
@AutoConfigureMockMvc(addFilters = false)
class KeyResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IKeyResultService keyResultService;

    @Autowired
    private ObjectMapper objectMapper;

    private KeyResultDto keyResultDto;

    @BeforeEach
    void setUp() {
        keyResultDto = new KeyResultDto();
        keyResultDto.setAreaId(1L);
        keyResultDto.setOwnerId(1L);
        keyResultDto.setCode("KR1");
        keyResultDto.setDescription("Test KR");
        keyResultDto.setMetricName("Test Metric");
        keyResultDto.setDataType(DataType.INTEGER);
        keyResultDto.setBaselineValue(BigDecimal.ZERO);
        keyResultDto.setTargetValue(BigDecimal.TEN);
        keyResultDto.setCurrentValue(BigDecimal.ONE);
        keyResultDto.setStatus(Status.ON_TRACK);
    }

    @Test
    void createKeyResult_ReturnsCreated() throws Exception {
        when(keyResultService.createKeyResult(any(KeyResultDto.class), eq(1L))).thenReturn(keyResultDto);

        mockMvc.perform(post("/keyresults/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(keyResultDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("KR1"))
                .andExpect(jsonPath("$.description").value("Test KR"));
    }
}
