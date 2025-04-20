package com.example.swift_codes_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "swift_codes")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftCode {

    // The 'swift_code' is the primary key, and it must be unique
    @Id
    @Column(name = "swift_code", nullable = false, length = 11)
    private String swiftCode;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "address")
    private String address;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "town_name")
    private String townName;

    @Column(name = "country_iso2", nullable = false, length = 2)
    private String countryISO2;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "is_headquarter", nullable = false)
    private boolean isHeadquarter;

    @Column(name = "headquarter_code", length = 11)
    private String headquarterCode;

    @Column(name = "time_zone")
    private String timeZone;

    // One-to-many relationship between headquarters and branches
    // A headquarter can have multiple branches (but not the other way around)

    @OneToMany(mappedBy = "headquarterCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude  // Exclude branches from toString to avoid infinite recursion
    private List<SwiftCode> branches = new ArrayList<>();
}
