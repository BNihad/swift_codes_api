package com.example.swift_codes_api.controller;

import com.example.swift_codes_api.dto.CountrySwiftCodesResponseDto;
import com.example.swift_codes_api.dto.SwiftCodeResponseDto;
import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.service.SwiftCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/swift-codes")
@RequiredArgsConstructor
public class SwiftCodeController {

    private final SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public ResponseEntity<SwiftCodeResponseDto> getSwiftCodeDetails(@PathVariable String swiftCode) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodeDetails(swiftCode));
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<CountrySwiftCodesResponseDto> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodesByCountry(countryISO2));
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> createSwiftCode(@RequestBody SwiftCode swiftCode) {
        swiftCodeService.createSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code added successfully"));
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<Map<String, String>> deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok(Map.of("message", "SWIFT code deleted successfully"));
    }
}
