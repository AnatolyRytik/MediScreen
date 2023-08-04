package com.mediscreen.patient.proxy;

import com.mediscreen.patient.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assessment-service", url = "${assessment-service-url}")
public interface AssessmentProxy {
    @GetMapping("/api/assessments/{id}")
    ReportDto getPatientRiskLevel(@PathVariable("id") Long id);
}
