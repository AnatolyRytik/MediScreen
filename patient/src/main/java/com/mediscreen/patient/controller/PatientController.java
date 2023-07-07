package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patientDto", new PatientDto());
        return "patients/add";
    }

    @PostMapping("/add")
    public String createPatient(@Valid @ModelAttribute("patientDto") PatientDto patientDto, BindingResult result) {
        if (result.hasErrors()) {
            return "patients/add";
        }
        patientService.createPatient(patientDto);
        return "redirect:/patients";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable @Min(1) Long id) {
        try {
            Patient patientDto = patientService.getPatient(id);
            return ResponseEntity.ok(patientDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(Model model, @PathVariable("id") Long id) {
        try {
            Patient patientDto = patientService.getPatient(id);
            model.addAttribute("patientDto", patientDto);
            return "patients/update";
        } catch (NotFoundException e) {
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @Valid @ModelAttribute("patientDto") PatientDto patientDto,
                                BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "patients/update";
            }
            patientService.updatePatient(id, patientDto);
            return "redirect:/patients";
        } catch (NotFoundException e) {
            return "error";
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable @Min(1) Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

