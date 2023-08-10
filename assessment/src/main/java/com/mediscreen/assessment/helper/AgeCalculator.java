package com.mediscreen.assessment.helper;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for age-related calculations.
 * <p>
 * This class provides a utility method for calculating the age of a person
 * based on their birth date.
 */
@NoArgsConstructor
public class AgeCalculator {

    /**
     * Calculates the age of a person based on their birth date.
     *
     * @param birthDate The birth date of the person as a {@link LocalDate}.
     * @return The age of the person in years.
     * @throws IllegalArgumentException if the birth date is null or is a future date.
     */
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
