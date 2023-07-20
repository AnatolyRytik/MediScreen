package com.mediscreen.notes.util.mapper;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between PatientNote and PatientNoteDto objects.
 */
@Component
public class PatientNoteMapper {
    private final ModelMapper modelMapper;

    /**
     * Constructs a new PatientNoteMapper instance.
     */
    public PatientNoteMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Maps a PatientNoteDto object to a PatientNote object.
     *
     * @param patientNoteDto the PatientNoteDto object
     * @return the mapped PatientNote object
     */
    public PatientNote toEntity(PatientNoteDto patientNoteDto) {
        return modelMapper.map(patientNoteDto, PatientNote.class);
    }

    /**
     * Maps a PatientNote object to a PatientNoteDto object.
     *
     * @param patientNote the PatientNote object
     * @return the mapped PatientNoteDto object
     */
    public PatientNoteDto toDto(PatientNote patientNote) {
        return modelMapper.map(patientNote, PatientNoteDto.class);
    }
}
