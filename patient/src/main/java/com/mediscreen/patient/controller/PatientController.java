package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.dto.ReportDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.proxy.AssessmentProxy;
import com.mediscreen.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling patient-related operations.
 */
@Slf4j
@Controller
public class PatientController {
    private final PatientService patientService;
    private final AssessmentProxy assessmentProxy;

    public PatientController(PatientService patientService, AssessmentProxy assessmentProxy) {
        this.patientService = patientService;
        this.assessmentProxy = assessmentProxy;
    }

    /**
     * Displays the form for adding a new patient.
     *
     * @param model the Thymeleaf model
     * @return the view name for the add patient form
     */
    @GetMapping("patients/add")
    public String showAddForm(Model model) {
        log.info("Displaying add patient form");
        model.addAttribute("patientDto", new PatientDto());
        return "patients/add";
    }

    /**
     * Handles the submission of the add patient form.
     *
     * @param patientDto the patient DTO containing the form data
     * @param result     the binding result for form validation
     * @return the redirect URL after successfully creating a patient, or the add patient form if there are validation errors
     */
    @PostMapping("patients/add")
    public String createPatient(@Valid @ModelAttribute("patientDto") PatientDto patientDto, BindingResult result) {
        log.info("Creating a new patient");
        if (result.hasErrors()) {
            return "patients/add";
        }
        patientService.createPatient(patientDto);
        return "redirect:/patients/all";
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param model the Thymeleaf model
     * @param id    the ID of the patient to retrieve
     * @return the view name for displaying the patient details, or the error page if the patient is not found
     */
    @GetMapping("patient/{id}")
    public String getPatient(Model model, @PathVariable("id") Long id) {
        log.info("Getting patient with ID: {}", id);
        Patient patient = patientService.getPatient(id);
        model.addAttribute("patient", patient);
        return "patients/patient";
    }

    /**
     * Retrieves all patients.
     *
     * @param model the Thymeleaf model
     * @return the view name for displaying the list of patients
     */
    @GetMapping("patients/all")
    public String getAllPatients(Model model) {
        log.info("Getting all patients");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/all";
    }

    /**
     * Displays the form for updating a patient.
     *
     * @param model the Thymeleaf model
     * @param id    the ID of the patient to update
     * @return the view name for the update patient form, or the error page if the patient is not found
     */
    @GetMapping("patient/{id}/update")
    public String showUpdateForm(Model model, @PathVariable("id") Long id) {
        log.info("Displaying update form for patient with ID: {}", id);
        Patient patient = patientService.getPatient(id);
        model.addAttribute("patientDto", patient);
        return "patients/update";
    }

    /**
     * Handles the submission of the update patient form.
     *
     * @param id         the ID of the patient to update
     * @param patientDto the patient DTO containing the form data
     * @param result     the binding result for form validation
     * @param model      the Thymeleaf model
     * @return the redirect URL after successfully updating a patient, or the update patient form if there are validation errors
     */
    @PostMapping("patient/{id}/update")
    public String updatePatient(@PathVariable("id") Long id, @Valid @ModelAttribute("patientDto") PatientDto patientDto,
                                BindingResult result, Model model) {
        log.info("Updating patient with ID: {}", id);
        if (result.hasErrors()) {
            return "patients/update";
        }
        patientService.updatePatient(id, patientDto);
        return "redirect:/patients/all";
    }

    /**
     * Handles the deletion of a patient.
     *
     * @param id    the ID of the patient to delete
     * @param model the Thymeleaf model
     * @return the redirect URL after successfully deleting a patient, or the error page if the patient is not found
     */
    @RequestMapping("patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id, Model model) {
        log.info("Deleting patient with ID: {}", id);
        patientService.deletePatient(id);
        return "redirect:/patients/all";
    }

    /**
     * Retrieves the assessment for a patient by ID.
     *
     * @param model     the Thymeleaf model
     * @param patientId the ID of the patient to retrieve the assessment for
     * @return the view name for displaying the patient assessment, or the error page if the patient is not found
     */
    @GetMapping("patient/assessment/{patientId}")
    public String getPatientAssessment(Model model, @PathVariable("patientId") Long patientId) {
        log.info("Getting assessment for patient with ID: {}", patientId);
        ReportDto report = assessmentProxy.getPatientRiskLevel(patientId);
        model.addAttribute("patient", report.getPatientDto());
        model.addAttribute("patientReport", report);
        return "patients/assessment";
    }

}
