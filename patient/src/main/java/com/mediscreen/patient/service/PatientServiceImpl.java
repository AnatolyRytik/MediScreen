package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.util.exception.NotFoundException;
import com.mediscreen.patient.util.mapper.PatientMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient createPatient(PatientDto PatientDto) {
        Patient patient = patientMapper.toEntity(PatientDto);
        Patient savedPatient = patientRepository.save(patient);
        return savedPatient;
    }

    @Override
    public PatientDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        return patientMapper.toDto(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList;
    }

    @Override
    public Patient updatePatient(Long id, PatientDto patientDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));

        Patient updatedPatient = patientMapper.toEntity(patientDTO);
        updatedPatient.setId(existingPatient.getId());

        Patient savedPatient = patientRepository.save(updatedPatient);
        return savedPatient;
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        patientRepository.deleteById(id);
    }
}

