package com.example.swift_codes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwiftCodeDetailDto {

    private String swiftCode;
    private String bankName;
    private String address;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
}
