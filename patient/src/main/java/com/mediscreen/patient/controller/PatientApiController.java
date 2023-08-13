package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patients")
@Slf4j
public class PatientApiController {
    private final PatientService patientService;

    public PatientApiController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Create a new patient.
     *
     * @param patientDto    the patient data transfer object containing details of the patient
     * @param bindingResult results of the validation on the patientDto
     * @return the created patient or a list of validation error messages
     */

    @Operation(description = "Create a new patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDto patientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        log.info("Creating a new patient: {}", patientDto);
        Patient createdPatient = patientService.createPatient(patientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }


    /**
     * Get a patient by ID.
     *
     * @param id the patient ID
     * @return the patient if found, or an error message if not found
     */
    @Operation(description = "Get a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content(mediaType = "application/json"
                    ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable @Min(1) Long id) {
        log.info("Getting patient with ID: {}", id);
        try {
            Patient patient = patientService.getPatient(id);
            return ResponseEntity.ok(patient);
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Get all patients.
     *
     * @return the list of all patients
     */
    @Operation(description = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        log.info("Getting all patients");
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * Update a patient by ID.
     *
     * @param id            the unique identifier of the patient to be updated
     * @param patientDto    the patient data transfer object containing the updated details of the patient
     * @param bindingResult results of the validation on the patientDto
     * @return ResponseEntity with the updated patient details if found, or an error message if not found or validation errors occur
     */
    @Operation(description = "Update a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request due to validation errors",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable @Min(1) Long id,
                                           @Valid @RequestBody PatientDto patientDto,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.error("Validation errors occurred: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        log.info("Updating patient with ID: {}", id);
        try {
            Patient updatedPatient = patientService.updatePatient(id, patientDto);
            return ResponseEntity.ok(updatedPatient);
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * Delete a patient by ID.
     *
     * @param id the patient ID
     * @return a response with no content if the patient is deleted, or an error message if not found
     */
    @Operation(description = "Delete a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable @Min(1) Long id) {
        log.info("Deleting patient with ID: {}", id);
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
