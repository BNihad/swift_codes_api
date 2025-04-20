package com.example.swift_codes_api.repository;

import com.example.swift_codes_api.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {

    List<SwiftCode> findBySwiftCode(String swiftCode);

    List<SwiftCode> findByCountryISO2(String countryISO2);

    List<SwiftCode> findByHeadquarterCode(String headquarterCode);


}
