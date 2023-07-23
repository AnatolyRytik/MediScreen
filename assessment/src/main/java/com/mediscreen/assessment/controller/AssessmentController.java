package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessments")
@Tag(name = "Assessments")
@Slf4j
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @Operation(description = "Get patient risk level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Report.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Report> getPatientRiskLevel(@PathVariable Long id) {
        log.info("Getting patient risk level for patient id: {}", id);
        Report report = assessmentService.getPatientRiskLevel(id);
        return ResponseEntity.ok(report);
    }
}
