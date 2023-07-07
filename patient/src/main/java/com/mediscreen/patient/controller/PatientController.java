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
            PatientDto patientDto = patientService.getPatient(id);
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


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable @Min(1) Long id, @Valid @RequestBody PatientDto patientDto) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patientDto);
            return ResponseEntity.ok(updatedPatient);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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

