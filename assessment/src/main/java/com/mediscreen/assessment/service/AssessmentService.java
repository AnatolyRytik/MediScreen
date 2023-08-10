package com.mediscreen.assessment.service;

import com.mediscreen.assessment.constants.RiskLevel;
import com.mediscreen.assessment.constants.TriggerTerms;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.PatientNoteDto;
import com.mediscreen.assessment.helper.AgeCalculator;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.proxy.PatientNotesProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for assessing a patient's risk level based on patient data and notes.
 */
@Service
public class AssessmentService {

    private final PatientProxy patientProxy;
    private final PatientNotesProxy patientNotesProxy;

    public AssessmentService(PatientProxy patientProxy, PatientNotesProxy patientNotesProxy) {
        this.patientProxy = patientProxy;
        this.patientNotesProxy = patientNotesProxy;
    }

    /**
     * Determines the risk level for a patient based on their age, gender, and notes.
     *
     * @param id The ID of the patient to assess.
     * @return A {@link Report} object containing the patient's details and their assessed risk level.
     */
    public Report getPatientRiskLevel(Long id) {
        PatientDto patient = patientProxy.getPatientById(id);
        int age = AgeCalculator.calculateAge(patient.getBirthDate());
        List<PatientNoteDto> patientNotes = patientNotesProxy.getAllPatientNotesByPatientId(id);
        RiskLevel riskLevel = calculateRiskLevel(age, patient.getGender(), patientNotes);
        return new Report(patient, age, riskLevel);
    }

    /**
     * Calculates the risk level based on the age, gender, and the number of trigger terms
     * found in the patient's notes.
     *
     * @param age          The age of the patient.
     * @param gender       The gender of the patient.
     * @param patientNotes The notes associated with the patient.
     * @return A {@link RiskLevel} enum value representing the determined risk level.
     */
    private RiskLevel calculateRiskLevel(int age, String gender, List<PatientNoteDto> patientNotes) {
        int triggerTermCount = 0;

        for (PatientNoteDto note : patientNotes) {
            for (TriggerTerms term : TriggerTerms.values()) {
                if (note.getNote().contains(term.getTriggerTerm())) {
                    triggerTermCount++;
                }
            }
        }

        if ((triggerTermCount >= 0 && triggerTermCount <= 1 && age > 30)
                || (gender.equalsIgnoreCase("M") && triggerTermCount >= 0 && triggerTermCount <= 2 && age < 30)
                || (gender.equalsIgnoreCase("F") && triggerTermCount >= 0 && triggerTermCount <= 3 && age < 30)) {
            return RiskLevel.NONE;
        } else if (triggerTermCount >= 2 && triggerTermCount < 6 && age > 30) {
            return RiskLevel.BORDERLINE;
        } else if ((age < 30 && gender.equalsIgnoreCase("M") && triggerTermCount >= 3 && triggerTermCount < 5)
                || (age < 30 && gender.equalsIgnoreCase("F") && triggerTermCount >= 4 && triggerTermCount < 7)
                || (age > 30 && triggerTermCount >= 6 && triggerTermCount < 8)) {
            return RiskLevel.IN_DANGER;
        } else if ((age < 30 && gender.equalsIgnoreCase("M") && triggerTermCount >= 5)
                || (age < 30 && gender.equalsIgnoreCase("F") && triggerTermCount >= 7)
                || (age > 30 && triggerTermCount >= 8)) {
            return RiskLevel.EARLY_ONSET;
        }

        return RiskLevel.NONE;
    }
}
