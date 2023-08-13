package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.util.exception.NotFoundException;
import com.mediscreen.patient.util.mapper.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PatientService interface.
 */
@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient createPatient(PatientDto patientDto) {
        log.info("Creating patient");
        Patient patient = patientMapper.toEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return savedPatient;
    }

    @Override
    public Patient getPatient(Long id) {
        log.info("Getting patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        return patient;
    }

    @Override
    public List<Patient> getAllPatients() {
        log.info("Getting all patients");
        List<Patient> patientList = patientRepository.findAll();
        return patientList;
    }

    @Override
    public Patient updatePatient(Long id, PatientDto patientDTO) {
        log.info("Updating patient with ID: {}", id);
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));

        Patient updatedPatient = patientMapper.toEntity(patientDTO);
        updatedPatient.setId(existingPatient.getId());

        Patient savedPatient = patientRepository.save(updatedPatient);
        return savedPatient;
    }

    @Override
    public void deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        patientRepository.deleteById(id);
    }
}
