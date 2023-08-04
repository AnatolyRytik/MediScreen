package com.mediscreen.patient.dto;

import lombok.Data;

@Data
public class ReportDto {
    private PatientDto patient;
    private int age;
    private String riskLevel;
}
