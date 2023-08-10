package com.mediscreen.patient.proxy;

import com.mediscreen.patient.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client proxy for communicating with the assessment service.
 */
@FeignClient(name = "assessment-service", url = "${assessment-service-url}")
public interface AssessmentProxy {

    /**
     * Fetches the risk level report for a patient with the given ID.
     *
     * @param id The ID of the patient for whom the risk level report is to be fetched.
     * @return The risk level report for the specified patient, represented as a {@link ReportDto} object.
     */
    @GetMapping("/api/assessments/{patientId}")
    ReportDto getPatientRiskLevel(@PathVariable("patientId") Long id);
}
