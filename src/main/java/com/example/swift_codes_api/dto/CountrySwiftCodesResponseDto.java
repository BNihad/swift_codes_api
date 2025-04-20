package com.example.swift_codes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountrySwiftCodesResponseDto {

    private String countryISO2;
    private String countryName;
    private List<SwiftCodeDetailDto> swiftCodes;
}
