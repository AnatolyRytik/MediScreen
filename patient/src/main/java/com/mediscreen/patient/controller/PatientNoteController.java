package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientNoteDto;
import com.mediscreen.patient.proxy.NotesProxy;
import com.mediscreen.patient.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling patient note-related operations.
 */
@Slf4j
@Controller
public class PatientNoteController {

    private final NotesProxy notesProxy;

    /**
     * Constructor for PatientNoteController.
     *
     * @param notesProxy the proxy for interacting with patient notes service.
     */
    public PatientNoteController(NotesProxy notesProxy) {
        this.notesProxy = notesProxy;
    }

    /**
     * Retrieves all notes for a given patient.
     *
     * @param model     Thymeleaf model for rendering view
     * @param patientId patient's unique identifier
     * @return view displaying all notes or error view
     */
    @GetMapping("patient/{patientId}/notes")
    public String getAllPatientNotes(Model model, @PathVariable("patientId") Long patientId) {
        log.info("Fetching all notes for patient with ID: {}", patientId);
        model.addAttribute("patientId", patientId);
        try {
            List<PatientNoteDto> patientNotes = notesProxy.getAllPatientNotesByPatientId(patientId);
            model.addAttribute("patientNotes", patientNotes);
            return "notes/all";
        } catch (RuntimeException e) {
            log.error("Failed to fetch notes for patient with ID: {}. Error: {}", patientId, e.getMessage());
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
    }

    /**
     * Displays the form for adding a new note for a patient.
     *
     * @param model     Thymeleaf model for rendering view
     * @param patientId patient's unique identifier
     * @return view to add a note
     */
    @GetMapping("patient/{patientId}/add-note")
    public String showAddNoteForm(Model model, @PathVariable("patientId") Long patientId) {
        log.info("Displaying add note form for patient with ID: {}", patientId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("noteDto", new PatientNoteDto());
        return "notes/add";
    }

    /**
     * Handles the submission of a new note for a patient.
     *
     * @param patientId patient's unique identifier
     * @param noteDto   the data transfer object representing the note
     * @param result    binding result indicating validation errors
     * @param model     Thymeleaf model for rendering view
     * @return view displaying all notes or error view
     */
    @PostMapping("patient/{patientId}/add-note")
    public String addNote(@PathVariable("patientId") Long patientId, @Valid @ModelAttribute("noteDto") PatientNoteDto noteDto, BindingResult result, Model model) {
        log.info("Creating a new note for patient with ID: {}", patientId);
        model.addAttribute("patientId", patientId);
        if (result.hasErrors()) {
            return "notes/add";
        }
        noteDto.setPatientId(patientId);
        notesProxy.createPatientNote(noteDto);
        return "redirect:/patient/" + patientId + "/notes";
    }

    /**
     * Displays the form for updating an existing note for a patient.
     *
     * @param model     Thymeleaf model for rendering view
     * @param patientId patient's unique identifier
     * @param noteId    the unique identifier of the note to be updated
     * @return view to update a note
     */
    @GetMapping("patient/{patientId}/update-note/{noteId}")
    public String showUpdateNoteForm(Model model, @PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId) {
        log.info("Displaying update note form for patient with ID: {} and note ID: {}", patientId, noteId);
        try {
            PatientNoteDto noteDto = notesProxy.getPatientNoteById(noteId);
            model.addAttribute("patientId", patientId);
            model.addAttribute("noteDto", noteDto);
            return "notes/update";
        } catch (NotFoundException e) {
            log.error("Note with ID: {} not found for patient with ID: {}. Error: {}", noteId, patientId, e.getMessage());
            model.addAttribute("errorMessage", "Note not found");
            return "error";
        }
    }

    /**
     * Handles the submission of an updated note for a patient.
     *
     * @param patientId patient's unique identifier
     * @param noteId    the unique identifier of the note to be updated
     * @param noteDto   the data transfer object representing the updated note
     * @param result    binding result indicating validation errors
     * @param model     Thymeleaf model for rendering view
     * @return view displaying all notes or error view
     */
    @PostMapping("patient/{patientId}/update-note/{noteId}")
    public String updateNote(@PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId, @Valid @ModelAttribute("noteDto") PatientNoteDto noteDto, BindingResult result, Model model) {
        log.info("Updating note with ID: {} for patient with ID: {}", noteId, patientId);
        if (result.hasErrors()) {
            return "notes/update";
        }
        noteDto.setPatientId(patientId);
        notesProxy.updatePatientNote(noteId, noteDto);
        return "redirect:/patient/" + patientId + "/notes";
    }

    /**
     * Handles the deletion of a note for a patient.
     *
     * @param patientId patient's unique identifier
     * @param noteId    the unique identifier of the note to be deleted
     * @param model     Thymeleaf model for rendering view
     * @return view displaying all notes or error view
     */
    @GetMapping("patient/{patientId}/delete-note/{noteId}")
    public String deleteNote(@PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId, Model model) {
        log.info("Deleting patient note with ID: {}", noteId);
        try {
            notesProxy.deletePatientNoteById(noteId);
            return "redirect:/patient/" + patientId + "/notes";
        } catch (NotFoundException e) {
            log.error("Failed to delete note with ID: {} for patient with ID: {}. Error: {}", noteId, patientId, e.getMessage());
            model.addAttribute("errorMessage", "Note not found");
            return "error";
        }
    }
}
