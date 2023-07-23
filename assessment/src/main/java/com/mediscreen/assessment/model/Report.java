package com.mediscreen.assessment.model;


import com.mediscreen.assessment.constants.RiskLevel;

import com.mediscreen.assessment.dto.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Report {
    private PatientDto patientData;
    private int age;
    private RiskLevel riskLevel;
}
