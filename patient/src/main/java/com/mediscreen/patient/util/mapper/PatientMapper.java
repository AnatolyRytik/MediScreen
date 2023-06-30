package com.mediscreen.patient.util.mapper;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    private final ModelMapper modelMapper;

    public PatientMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }

    public PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }
}
