package com.mediscreen.assessment.model;


import com.mediscreen.assessment.constants.RiskLevel;
import com.mediscreen.assessment.dto.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class for representing a assessment report.
 */
@Data
@AllArgsConstructor
public class Report {
    private PatientDto patientDto;
    private int age;
    private RiskLevel riskLevel;
}
