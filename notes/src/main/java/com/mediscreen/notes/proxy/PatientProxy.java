package com.mediscreen.notes.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Proxy interface for communication with the patient-microservice.
 * <p>
 * This interface uses Feign to simplify HTTP communication between microservices.
 */
@FeignClient(name = "patient-microservice", url = "localhost:8080")
public interface PatientProxy {

    /**
     * Retrieves the details of a specific patient based on the provided patient ID.
     *
     * @param id The ID of the patient.
     */
    @GetMapping("/api/patients/{id}")
    void getPatientById(@PathVariable("id") Long id);
}
