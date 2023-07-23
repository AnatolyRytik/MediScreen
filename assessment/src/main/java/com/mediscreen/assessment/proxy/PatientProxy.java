package com.mediscreen.assessment.proxy;

import com.mediscreen.assessment.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-microservice", url = "localhost:8080")
public interface PatientProxy {

    @GetMapping("/api/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") Long id);
}
