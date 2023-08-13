package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.util.exception.NotFoundException;
import com.mediscreen.patient.util.mapper.PatientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PatientServiceTests {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePatient() {
        // Arrange
        PatientDto patientDto = new PatientDto();
        Patient patient = new Patient();
        when(patientMapper.toEntity(patientDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient createdPatient = patientService.createPatient(patientDto);

        // Assert
        Assertions.assertEquals(patient, createdPatient);
        verify(patientMapper, times(1)).toEntity(patientDto);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testGetPatientById_ExistingPatient() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        Patient retrievedPatient = patientService.getPatient(id);

        // Assert
        Assertions.assertEquals(patient, retrievedPatient);
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPatientById_NonExistingPatient() {
        // Arrange
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> patientService.getPatient(id));
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        List<PatientDto> patientDtoList = patients.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> retrievedPatients = patientService.getAllPatients();

        // Assert
        Assertions.assertEquals(patients.size(), retrievedPatients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePatient_ExistingPatient() {
        // Arrange
        Long id = 1L;
        PatientDto patientDto = new PatientDto();
        Patient existingPatient = new Patient();
        Patient updatedPatient = new Patient();
        when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
        when(patientMapper.toEntity(patientDto)).thenReturn(updatedPatient);
        when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);

        // Act
        Patient result = patientService.updatePatient(id, patientDto);

        // Assert
        Assertions.assertEquals(updatedPatient, result);
        verify(patientRepository, times(1)).findById(id);
        verify(patientMapper, times(1)).toEntity(patientDto);
        verify(patientRepository, times(1)).save(updatedPatient);
    }

    @Test
    public void testUpdatePatient_NonExistingPatient() {
        // Arrange
        Long id = 1L;
        PatientDto patientDto = new PatientDto();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> patientService.updatePatient(id, patientDto));
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    public void testDeletePatient_ExistingPatient() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        patientService.deletePatient(id);

        // Assert
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletePatient_NonExistingPatient() {
        // Arrange
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> patientService.deletePatient(id));
        verify(patientRepository, times(1)).findById(id);
    }
}
