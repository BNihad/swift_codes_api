package com.example.swift_codes_api.service;

import com.example.swift_codes_api.dto.BranchDto;
import com.example.swift_codes_api.dto.CountrySwiftCodesResponseDto;
import com.example.swift_codes_api.dto.SwiftCodeDetailDto;
import com.example.swift_codes_api.dto.SwiftCodeResponseDto;
import com.example.swift_codes_api.exception.InvalidInputException;
import com.example.swift_codes_api.exception.ResourceNotFoundException;
import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.repository.SwiftCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwiftCodeService {

    private final SwiftCodeRepository swiftCodeRepository;


    @Transactional(readOnly = true)
    public CountrySwiftCodesResponseDto getSwiftCodesByCountry(String countryISO2) {
        List<SwiftCode> swiftCodes = swiftCodeRepository.findByCountryISO2(countryISO2.toUpperCase());
        if (swiftCodes.isEmpty()) {
            throw new ResourceNotFoundException("No SWIFT codes found for country: " + countryISO2);
        }
        String countryName = swiftCodes.get(0).getCountryName();

        List<SwiftCodeDetailDto> swiftCodeDetails = swiftCodes.stream()
                .map(code -> SwiftCodeDetailDto.builder()
                        .address(code.getAddress())
                        .bankName(code.getBankName())
                        .countryISO2(code.getCountryISO2())
                        .isHeadquarter(code.isHeadquarter())
                        .swiftCode(code.getSwiftCode())
                        .build())
                .collect(Collectors.toList());

        System.out.println("Found " + swiftCodeDetails.size() + " swift codes for country " + countryISO2);

        return CountrySwiftCodesResponseDto.builder()
                .countryISO2(countryISO2.toUpperCase())
                .countryName(countryName)
                .swiftCodes(swiftCodeDetails)
                .build();
    }


    @Transactional(readOnly = true)
    public SwiftCodeResponseDto getSwiftCodeDetails(String swiftCode) {
        SwiftCode code = swiftCodeRepository.findById(swiftCode)
                .orElseThrow(() -> new ResourceNotFoundException("SWIFT code not found: " + swiftCode));

        List<BranchDto> branchDtos = null;
        if (code.isHeadquarter()) {
            List<SwiftCode> branches = swiftCodeRepository.findByHeadquarterCode(swiftCode);
            branchDtos = branches.stream()
                    .map(branch -> new BranchDto(branch.getSwiftCode(), branch.getBankName(), branch.getAddress(), branch.getCountryISO2(), branch.getCountryName(), false))
                    .collect(Collectors.toList());
        }

        return new SwiftCodeResponseDto(
                code.getSwiftCode(),
                code.getBankName(),
                code.getAddress(),
                code.getCountryISO2(),
                code.getCountryName(),
                code.isHeadquarter(),
                branchDtos != null ? branchDtos : List.of()
        );
    }

    public SwiftCode createSwiftCode(SwiftCode swiftCode) {
        if (swiftCode.getSwiftCode() == null || swiftCode.getSwiftCode().isEmpty()) {
            throw new InvalidInputException("SWIFT code must not be empty.");
        }
        return swiftCodeRepository.save(swiftCode);
    }


    public void deleteSwiftCode(String swiftCode) {
        swiftCodeRepository.deleteById(swiftCode);
    }
}
