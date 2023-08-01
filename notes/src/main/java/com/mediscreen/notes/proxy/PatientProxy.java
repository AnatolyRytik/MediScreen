package com.mediscreen.notes.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-microservice", url = "localhost:8080")
public interface PatientProxy {

    @GetMapping("/api/patients/{id}")
    void getPatientById(@PathVariable("id") Long id);
}
