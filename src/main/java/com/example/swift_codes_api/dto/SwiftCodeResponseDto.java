package com.example.swift_codes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwiftCodeResponseDto {

    private String swiftCode;
    private String bankName;
    private String address;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private List<BranchDto> branches;
}
