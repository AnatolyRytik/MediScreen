package com.mediscreen.patient.proxy;

import com.mediscreen.patient.dto.PatientNoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "notes-service", url = "${notes-service-url}")
public interface NotesProxy {

    @GetMapping("/api/patient-notes/patient/{patientId}")
    List<PatientNoteDto> getAllPatientNotesByPatientId(@PathVariable("patientId") Long patientId);

    @PostMapping("/api/patient-notes")
    PatientNoteDto createPatientNote(@RequestBody PatientNoteDto patientNoteDto);

    @PutMapping("/api/patient-notes/{id}")
    PatientNoteDto updatePatientNote(@PathVariable("id") String id, @RequestBody PatientNoteDto patientNoteDto);

    @DeleteMapping("/api/patient-notes/{id}")
    void deletePatientNoteById(@PathVariable("id") String id);
}
