package com.mediscreen.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * (DTO) for Report for the patient.
 * This class represents the data to get patient report.
 */
@Data
@AllArgsConstructor
public class ReportDto {
    private PatientDto patientDto;
    private int age;
    private String riskLevel;
}
