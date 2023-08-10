package com.mediscreen.assessment.constants;

import lombok.Getter;

/**
 * Enum representing the various risk levels for patients.
 */
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
