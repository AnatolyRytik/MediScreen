package com.mediscreen.patient.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

@Data
public class PatientDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    private String gender;

    private String address;

    private String phoneNumber;

    public LocalDate getBirthDate() {
        return birthDate;
    }


}

