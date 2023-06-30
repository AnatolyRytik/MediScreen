package com.mediscreen.patient.dto;

import lombok.Data;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

@Data
public class PatientDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private LocalDate birthDate;

    @NotBlank
    private String gender;

    private String address;

    private String phoneNumber;

}

