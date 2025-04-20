package com.example.swift_codes_api.repository;

import com.example.swift_codes_api.model.SwiftCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class SwiftCodeRepositoryTest {

    @Autowired
    private SwiftCodeRepository repository;

    @Test
    void testFindBySwiftCode() {
        SwiftCode swiftCode = SwiftCode.builder()
                .swiftCode("TESTPLPPXXX")
                .bankName("Polski Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .address("Warsaw")
                .isHeadquarter(true)
                .build();

        repository.save(swiftCode);

        List<SwiftCode> result = repository.findBySwiftCode("TESTPLPPXXX");
        assertEquals(1, result.size());
        assertEquals("Polski Bank", result.get(0).getBankName());
    }

    @Test
    void testFindByCountryISO2() {
        SwiftCode swiftCode = SwiftCode.builder()
                .swiftCode("FRBKFRPPXXX")
                .bankName("French Bank")
                .countryISO2("FR")
                .countryName("FRANCE")
                .address("Paris")
                .isHeadquarter(true)
                .build();

        repository.save(swiftCode);

        List<SwiftCode> result = repository.findByCountryISO2("FR");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByHeadquarterCode() {
        SwiftCode hq = SwiftCode.builder()
                .swiftCode("DEUTDEFFXXX")
                .bankName("Deutsche Bank")
                .countryISO2("DE")
                .countryName("GERMANY")
                .address("Berlin")
                .isHeadquarter(true)
                .build();

        SwiftCode branch = SwiftCode.builder()
                .swiftCode("DEUTDEFF123")
                .headquarterCode("DEUTDEFFXXX")
                .bankName("Deutsche Branch")
                .countryISO2("DE")
                .countryName("GERMANY")
                .address("Munich")
                .isHeadquarter(false)
                .build();

        repository.save(hq);
        repository.save(branch);

        List<SwiftCode> branches = repository.findByHeadquarterCode("DEUTDEFFXXX");
        assertEquals(1, branches.size());
        assertEquals("Munich", branches.get(0).getAddress());
    }
}
