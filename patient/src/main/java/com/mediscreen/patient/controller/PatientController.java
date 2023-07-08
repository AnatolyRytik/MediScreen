package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.exception.NotFoundException;
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
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Displays the form for adding a new patient.
     *
     * @param model the Thymeleaf model
     * @return the view name for the add patient form
     */
    @GetMapping("/add")
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
    @PostMapping("/add")
    public String createPatient(@Valid @ModelAttribute("patientDto") PatientDto patientDto, BindingResult result) {
        log.info("Creating a new patient");
        if (result.hasErrors()) {
            return "patients/add";
        }
        patientService.createPatient(patientDto);
        return "redirect:/patients";
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param model the Thymeleaf model
     * @param id    the ID of the patient to retrieve
     * @return the view name for displaying the patient details, or the error page if the patient is not found
     */
    @GetMapping("/{id}")
    public String getPatient(Model model, @PathVariable("id") Long id) {
        log.info("Getting patient with ID: {}", id);
        try {
            Patient patient = patientService.getPatient(id);
            model.addAttribute("patient", patient);
            return "patients/patient";
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
    }

    /**
     * Retrieves all patients.
     *
     * @param model the Thymeleaf model
     * @return the view name for displaying the list of patients
     */
    @GetMapping
    public String getAllPatients(Model model) {
        log.info("Getting all patients");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    /**
     * Displays the form for updating a patient.
     *
     * @param model the Thymeleaf model
     * @param id    the ID of the patient to update
     * @return the view name for the update patient form, or the error page if the patient is not found
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(Model model, @PathVariable("id") Long id) {
        log.info("Displaying update form for patient with ID: {}", id);
        try {
            Patient patientDto = patientService.getPatient(id);
            model.addAttribute("patientDto", patientDto);
            return "patients/update";
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
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
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @Valid @ModelAttribute("patientDto") PatientDto patientDto,
                                BindingResult result, Model model) {
        log.info("Updating patient with ID: {}", id);
        try {
            if (result.hasErrors()) {
                return "patients/update";
            }
            patientService.updatePatient(id, patientDto);
            return "redirect:/patients";
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
    }

    /**
     * Handles the deletion of a patient.
     *
     * @param id    the ID of the patient to delete
     * @param model the Thymeleaf model
     * @return the redirect URL after successfully deleting a patient, or the error page if the patient is not found
     */
    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id, Model model) {
        log.info("Deleting patient with ID: {}", id);
        try {
            patientService.deletePatient(id);
            return "redirect:/patients";
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
    }
}
