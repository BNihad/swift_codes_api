package com.example.swift_codes_api.service;


import com.example.swift_codes_api.model.SwiftCode;
import com.example.swift_codes_api.repository.SwiftCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SwiftCodeExcelImportService {

    private static final String EXCEL_FILENAME = "SWIFT_CODES.xlsx";
    private final SwiftCodeRepository swiftCodeRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void importSwiftCodes() {
        if (swiftCodeRepository.count() > 0) {
            log.info("Swift codes already exist in the database. Skipping import.");
            return;
        }

        try {
            log.info("Loading SWIFT codes from Excel file: {}", EXCEL_FILENAME);
            List<SwiftCode> swiftCodes = parseExcelFile();
            swiftCodeRepository.saveAll(swiftCodes);
            log.info("Successfully imported {} SWIFT codes.", swiftCodes.size());
        } catch (IOException e) {
            log.error("Failed to import SWIFT codes: {}", e.getMessage(), e);
        }
    }

    private List<SwiftCode> parseExcelFile() throws IOException {
        List<SwiftCode> result = new ArrayList<>();

        try (InputStream is = new ClassPathResource(EXCEL_FILENAME).getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String countryISO2 = getCellValue(row.getCell(0));
                String swiftCode = getCellValue(row.getCell(1));
                String codeType = getCellValue(row.getCell(2));
                String bankName = getCellValue(row.getCell(3));
                String address = getCellValue(row.getCell(4));
                String townName = getCellValue(row.getCell(5));
                String countryName = getCellValue(row.getCell(6));
                String timeZone = getCellValue(row.getCell(7));

                if (swiftCode == null || swiftCode.isEmpty()) {
                    log.warn("Skipping row {}: Missing SWIFT code", row.getRowNum());
                    continue;
                }

                boolean isHeadquarter = swiftCode.endsWith("XXX");

                String fullAddress = (address != null ? address : "");
                if (townName != null && !fullAddress.contains(townName)) {
                    fullAddress = fullAddress.isEmpty() ? townName : fullAddress + ", " + townName;
                }

                SwiftCode.SwiftCodeBuilder builder = SwiftCode.builder()
                        .swiftCode(swiftCode)
                        .bankName(bankName)
                        .address(fullAddress)
                        .countryISO2(countryISO2 != null ? countryISO2.toUpperCase() : null)
                        .countryName(countryName != null ? countryName.toUpperCase() : null)
                        .isHeadquarter(isHeadquarter)
                        .timeZone(timeZone)
                        .codeType(codeType)
                        .townName(townName);

                if (!isHeadquarter && swiftCode.length() >= 8) {
                    String headquarterCode = swiftCode.substring(0, 8) + "XXX";
                    builder.headquarterCode(headquarterCode);
                }

                result.add(builder.build());
            }
        }

        return result;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}
