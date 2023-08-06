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
 * Controller class for handling patient-related operations.
 */
@Slf4j
@Controller
public class PatientNoteController {

    private final NotesProxy notesProxy;


    public PatientNoteController(NotesProxy notesProxy) {
        this.notesProxy = notesProxy;
    }

    @GetMapping("patient/{patientId}/notes")
    public String getAllPatientNotes(Model model, @PathVariable("patientId") Long patientId) {
        model.addAttribute("patientId", patientId);
        try {
            List<PatientNoteDto> patientNotes = notesProxy.getAllPatientNotesByPatientId(patientId);
            model.addAttribute("patientNotes", patientNotes);
            return "notes/all";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Patient not found");
            return "error";
        }
    }

    @GetMapping("patient/{patientId}/add-note")
    public String showAddNoteForm(Model model, @PathVariable("patientId") Long patientId) {
        log.info("Displaying add note form");
        model.addAttribute("patientId", patientId);
        model.addAttribute("noteDto", new PatientNoteDto());
        return "notes/add";
    }

    @PostMapping("patient/{patientId}/add-note")
    public String addNote(@PathVariable("patientId") Long patientId, @Valid @ModelAttribute("noteDto") PatientNoteDto noteDto, BindingResult result, Model model) {
        log.info("Creating a new note");
        model.addAttribute("patientId", patientId);
        if (result.hasErrors()) {
            return "notes/add";
        }
        noteDto.setPatientId(patientId);
        notesProxy.createPatientNote(noteDto);
        return "redirect:/patient/" + patientId + "/notes";
    }

    @GetMapping("patient/{patientId}/update-note/{noteId}")
    public String showUpdateNoteForm(Model model, @PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId) {
        try {
            PatientNoteDto noteDto = notesProxy.getPatientNoteById(noteId);
            model.addAttribute("patientId", patientId);
            model.addAttribute("noteDto", noteDto);
            return "notes/update";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "Note not found");
            return "error";
        }
    }

    @PostMapping("patient/{patientId}/update-note/{noteId}")
    public String updateNote(@PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId, @Valid @ModelAttribute("noteDto") PatientNoteDto noteDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "notes/update";
        }
        noteDto.setPatientId(patientId);
        notesProxy.updatePatientNote(noteId, noteDto);
        return "redirect:/patient/" + patientId + "/notes";
    }

    @GetMapping("patient/{patientId}/delete-note/{noteId}")
    public String deleteNote(@PathVariable("patientId") Long patientId, @PathVariable("noteId") String noteId, Model model) {
        log.info("Deleting patient note with ID: {}", noteId);
        try {
            notesProxy.deletePatientNoteById(noteId);
            return "redirect:/patient/" + patientId + "/notes";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "Note not found");
            return "error";
        }
    }
}
