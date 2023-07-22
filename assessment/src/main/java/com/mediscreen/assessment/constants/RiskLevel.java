package com.mediscreen.assessment.constants;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset");

    private final String riskLevel;

    RiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
