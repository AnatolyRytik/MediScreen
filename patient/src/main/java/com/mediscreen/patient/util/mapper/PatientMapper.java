package com.mediscreen.patient.util.mapper;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between Patient and PatientDto objects.
 */
@Component
public class PatientMapper {
    private final ModelMapper modelMapper;

    /**
     * Constructs a new PatientMapper instance.
     */
    public PatientMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Maps a PatientDto object to a Patient object.
     *
     * @param patientDto the PatientDto object
     * @return the mapped Patient object
     */
    public Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }

    /**
     * Maps a Patient object to a PatientDto object.
     *
     * @param patient the Patient object
     * @return the mapped PatientDto object
     */
    public PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }
}
