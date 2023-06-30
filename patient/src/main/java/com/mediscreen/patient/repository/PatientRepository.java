package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findById(long id);

    List<Patient> findByFirstNameAndLastName(String firstName, String lastName);

    List<Patient> findByBirthDate(LocalDate birthDate);
}
