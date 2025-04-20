package com.example.swift_codes_api.service;

import com.example.swift_codes_api.dto.CountrySwiftCodesResponseDto;
import com.example.swift_codes_api.dto.SwiftCodeResponseDto;
import com.example.swift_codes_api.exception.ResourceNotFoundException;
import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.repository.SwiftCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    private SwiftCode hqCode;
    private SwiftCode branchCode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hqCode = SwiftCode.builder()
                .swiftCode("BANKUS33XXX")
                .bankName("Bank HQ")
                .address("Main Street")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .build();

        branchCode = SwiftCode.builder()
                .swiftCode("BANKUS33NYC")
                .bankName("Bank Branch")
                .address("Branch Street")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(false)
                .headquarterCode("BANKUS33XXX")
                .build();
    }

    @Test
    void getSwiftCodeDetails_ShouldReturnHQWithBranches() {
        when(swiftCodeRepository.findById("BANKUS33XXX")).thenReturn(Optional.of(hqCode));
        when(swiftCodeRepository.findByHeadquarterCode("BANKUS33XXX")).thenReturn(List.of(branchCode));

        SwiftCodeResponseDto response = swiftCodeService.getSwiftCodeDetails("BANKUS33XXX");

        assertEquals("BANKUS33XXX", response.getSwiftCode());
        assertTrue(response.isHeadquarter());
        assertEquals(1, response.getBranches().size());
        response.getBranches().get(0).getSwiftCode();
    }

    @Test
    void getSwiftCodeDetails_NotFound_ShouldThrow() {
        when(swiftCodeRepository.findById("UNKNOWN")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> swiftCodeService.getSwiftCodeDetails("UNKNOWN"));
    }

    @Test
    void getSwiftCodesByCountry_ShouldReturnData() {
        when(swiftCodeRepository.findByCountryISO2("US")).thenReturn(List.of(hqCode, branchCode));

        CountrySwiftCodesResponseDto response = swiftCodeService.getSwiftCodesByCountry("us");

        assertEquals("US", response.getCountryISO2());
        assertEquals(2, response.getSwiftCodes().size());
    }

    @Test
    void createSwiftCode_ShouldSaveSuccessfully() {
        when(swiftCodeRepository.save(hqCode)).thenReturn(hqCode);
        SwiftCode saved = swiftCodeService.createSwiftCode(hqCode);
        assertEquals("BANKUS33XXX", saved.getSwiftCode());
    }

    @Test
    void deleteSwiftCode_ShouldCallRepository() {
        swiftCodeService.deleteSwiftCode("BANKUS33XXX");
        verify(swiftCodeRepository).deleteById("BANKUS33XXX");
    }
}
