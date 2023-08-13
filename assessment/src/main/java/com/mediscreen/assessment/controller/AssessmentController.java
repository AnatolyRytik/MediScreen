package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.exception.NotFoundException;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.service.AssessmentService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for managing patient risk assessments.
 */
@RestController
@RequestMapping("/api/assessments")
@Tag(name = "Assessments")
@Slf4j
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * Retrieves the risk level for a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return A ResponseEntity containing the risk report or an error message.
     */
    @Operation(description = "Get patient risk level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Report.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientRiskLevel(@PathVariable Long id) {
        log.info("Getting patient risk level for patient id: {}", id);
        try {
            Report report = assessmentService.getPatientRiskLevel(id);
            return ResponseEntity.ok(report);
        } catch (NotFoundException e) {
            log.error("Patient not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with ID: " + id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error occurred while fetching patient risk level: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching patient risk level.");
        }
    }
}
