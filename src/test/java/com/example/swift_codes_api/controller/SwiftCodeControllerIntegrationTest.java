package com.example.swift_codes_api.controller;

import com.example.swift_codes_api.dto.CountrySwiftCodesResponseDto;
import com.example.swift_codes_api.dto.SwiftCodeDetailDto;
import com.example.swift_codes_api.dto.SwiftCodeResponseDto;
import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.service.SwiftCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SwiftCodeControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftCodeService swiftCodeService;

    @InjectMocks
    private SwiftCodeController swiftCodeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(swiftCodeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetSwiftCodeDetails() throws Exception {
        SwiftCodeResponseDto dto = new SwiftCodeResponseDto(
                "BANKUS33XXX", "Bank A", "Main Street", "US", "UNITED STATES", true, List.of()
        );

        when(swiftCodeService.getSwiftCodeDetails("BANKUS33XXX")).thenReturn(dto);

        mockMvc.perform(get("/v1/swift-codes/BANKUS33XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("BANKUS33XXX"))
                .andExpect(jsonPath("$.headquarter").value(true)); // or use isHeadquarter with @JsonProperty
    }

    @Test
    void testGetSwiftCodesByCountry() throws Exception {
        CountrySwiftCodesResponseDto dto = CountrySwiftCodesResponseDto.builder()
                .countryISO2("US")
                .countryName("UNITED STATES")
                .swiftCodes(List.of(
                        SwiftCodeDetailDto.builder().swiftCode("BANKUSX1").isHeadquarter(true).build(),
                        SwiftCodeDetailDto.builder().swiftCode("BANKUSX2").isHeadquarter(false).build()
                ))
                .build();

        when(swiftCodeService.getSwiftCodesByCountry("US")).thenReturn(dto);

        mockMvc.perform(get("/v1/swift-codes/country/US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCodes.length()").value(2));
    }

    @Test
    void testAddSwiftCode() throws Exception {
        SwiftCode swiftCode = SwiftCode.builder()
                .swiftCode("BANKUSNEW")
                .bankName("Bank New")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .address("Wall Street")
                .isHeadquarter(true)
                .build();

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(swiftCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code added successfully"));
    }

    @Test
    void testDeleteSwiftCode() throws Exception {
        mockMvc.perform(delete("/v1/swift-codes/BANKDELETE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code deleted successfully"));
    }
}
