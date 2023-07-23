package com.mediscreen.assessment.helper;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
public class AgeCalculator {

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        LocalDate currentDate = LocalDate.now();
        if (birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return Period.between(birthDate, currentDate).getYears();
    }
}
