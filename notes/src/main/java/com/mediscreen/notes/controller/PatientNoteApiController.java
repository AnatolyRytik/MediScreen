package com.mediscreen.notes.controller;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.service.PatientNoteService;
import com.mediscreen.notes.util.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patient-notes")
@Tag(name = "Patient Notes")
@Slf4j
public class PatientNoteApiController {
    private final PatientNoteService patientNoteService;

    public PatientNoteApiController(PatientNoteService patientNoteService) {
        this.patientNoteService = patientNoteService;
    }

    @Operation(description = "Get all patient notes for all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientNote.class))))
    })
    @GetMapping("/all")
    public ResponseEntity<List<PatientNote>> getAllPatientNotes() {
        log.info("Getting all patient notes for all patients");
        List<PatientNote> allPatientNotes = patientNoteService.getAllPatientNotes();
        return ResponseEntity.ok(allPatientNotes);
    }


    @Operation(description = "Create a new patient note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient note created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientNote.class)))
    })
    @PostMapping
    public ResponseEntity<PatientNote> createPatientNote(@Valid @RequestBody PatientNoteDto patientNoteDto) {
        log.info("Creating a new patient note: {}", patientNoteDto);
        PatientNote createdPatientNote = patientNoteService.createPatientNote(patientNoteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatientNote);
    }

    @Operation(description = "Get a patient note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientNote.class))),
            @ApiResponse(responseCode = "404", description = "Patient note not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientNote(@PathVariable String id) {
        log.info("Getting patient note with ID: {}", id);
        try {
            PatientNote patientNote = patientNoteService.getPatientNoteById(id);
            return ResponseEntity.ok(patientNote);
        } catch (NotFoundException e) {
            log.error("Patient note not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(description = "Get all patient notes for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientNote.class))))
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientNote>> getAllPatientNotesByPatientId(@PathVariable Long patientId) {
        log.info("Getting all patient notes for patient with ID: {}", patientId);
        List<PatientNote> patientNotes = patientNoteService.getAllPatientNotesByPatientId(patientId);
        return ResponseEntity.ok(patientNotes);
    }

    @Operation(description = "Update a patient note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient note updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientNote.class))),
            @ApiResponse(responseCode = "404", description = "Patient note not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatientNote(@PathVariable String id, @Valid @RequestBody PatientNoteDto patientNoteDto) {
        log.info("Updating patient note with ID: {}", id);
        try {
            PatientNote updatedPatientNote = patientNoteService.updatePatientNote(id, patientNoteDto);
            return ResponseEntity.ok(updatedPatientNote);
        } catch (NotFoundException e) {
            log.error("Patient note not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(description = "Delete a patient note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient note deleted"),
            @ApiResponse(responseCode = "404", description = "Patient note not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientNote(@PathVariable String id) {
        log.info("Deleting patient note with ID: {}", id);
        try {
            patientNoteService.deletePatientNoteById(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Patient note not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
