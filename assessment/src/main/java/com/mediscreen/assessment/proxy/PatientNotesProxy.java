package com.mediscreen.assessment.proxy;

import com.mediscreen.assessment.dto.PatientNoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-note-microservice", url = "localhost:8082")
public interface PatientNotesProxy {

    @GetMapping("/api/patient-notes/{id}")
    List<PatientNoteDto> getPatientNotes(@PathVariable("id") Long id);
}
