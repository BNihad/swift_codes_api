package com.example.swift_codes_api.controller;

import com.example.swift_codes_api.dto.CountrySwiftCodesResponseDto;
import com.example.swift_codes_api.dto.SwiftCodeDetailDto;
import com.example.swift_codes_api.dto.SwiftCodeResponseDto;
import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.service.SwiftCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SwiftCodeControllerUnitTest {

    @Mock
    private SwiftCodeService swiftCodeService;

    @InjectMocks
    private SwiftCodeController swiftCodeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSwiftCodeDetails_ShouldReturnSwiftCodeResponseDto() {
        String swiftCode = "TESTUS33XXX";

        SwiftCodeResponseDto expected = new SwiftCodeResponseDto(
                swiftCode, "Test Bank", "123 Main St", "US", "UNITED STATES", true, List.of()
        );

        when(swiftCodeService.getSwiftCodeDetails(swiftCode)).thenReturn(expected);

        ResponseEntity<SwiftCodeResponseDto> response = swiftCodeController.getSwiftCodeDetails(swiftCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getSwiftCodesByCountry_ShouldReturnCountrySwiftCodesResponseDto() {
        String countryISO2 = "US";

        CountrySwiftCodesResponseDto expected = CountrySwiftCodesResponseDto.builder()
                .countryISO2("US")
                .countryName("UNITED STATES")
                .swiftCodes(List.of(
                        SwiftCodeDetailDto.builder().swiftCode("BANKUSXX").bankName("BANK 1").isHeadquarter(true).build()
                ))
                .build();

        when(swiftCodeService.getSwiftCodesByCountry("US")).thenReturn(expected);

        ResponseEntity<CountrySwiftCodesResponseDto> response = swiftCodeController.getSwiftCodesByCountry("US");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }

    @Test
    void createSwiftCode_ShouldReturnSuccessMessage() {
        SwiftCode swiftCode = SwiftCode.builder()
                .swiftCode("NEWCODEXX")
                .bankName("Test Bank")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .address("123 Test Street")
                .build();

        ResponseEntity<Map<String, String>> response = swiftCodeController.createSwiftCode(swiftCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SWIFT code added successfully", response.getBody().get("message"));
        verify(swiftCodeService, times(1)).createSwiftCode(swiftCode);
    }

    @Test
    void deleteSwiftCode_ShouldReturnSuccessMessage() {
        String swiftCode = "DELCODEXX";

        ResponseEntity<Map<String, String>> response = swiftCodeController.deleteSwiftCode(swiftCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SWIFT code deleted successfully", response.getBody().get("message"));
        verify(swiftCodeService, times(1)).deleteSwiftCode(swiftCode);
    }
}
