package com.mediscreen.patient.config;

import com.mediscreen.patient.util.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling exceptions throughout the application.
 * <p>
 * This class provides centralized exception handling across all {@code @RequestMapping} methods.
 * Instead of handling exceptions in individual controllers, this class provides a mechanism to handle
 * them globally, ensuring a consistent response to clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type {@link NotFoundException}.
     *
     * @param e     The caught NotFoundException.
     * @param model The model to which attributes can be added and then rendered in the view.
     * @return The name of the error view to be rendered.
     */
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    /**
     * Handles all other exceptions not specifically addressed by more targeted exception handlers.
     *
     * @param e     The caught exception.
     * @param model The model to which attributes can be added and then rendered in the view.
     * @return The name of the error view to be rendered.
     */
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception e, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
        return "error";
    }
}
