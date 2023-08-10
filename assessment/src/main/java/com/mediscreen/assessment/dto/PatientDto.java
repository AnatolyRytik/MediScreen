package com.mediscreen.assessment.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO class for representing a patient.
 */
@Data
public class PatientDto {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String address;

    private String phoneNumber;
}
