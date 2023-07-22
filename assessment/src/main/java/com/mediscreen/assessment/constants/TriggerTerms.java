package com.mediscreen.assessment.constants;

import lombok.Getter;

@Getter
public enum TriggerTerms {
    HEMOGLOBINE_A1C("Hémoglobine A1C"),
    MICROALBUMINE("Microalbumine"),
    HAUTEUR("Hauteur"),
    POIDS("Poids"),
    FUMEUR("Fumeur"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGE("Vertige"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORPS("Anticorps");

    private final String triggerTerm;

    TriggerTerms(String triggerTerm) {
        this.triggerTerm = triggerTerm;
    }
}
