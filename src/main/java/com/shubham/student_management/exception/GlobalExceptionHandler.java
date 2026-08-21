package com.shubham.student_management.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CsrfException.class)
    public String csrfException(
            CsrfException exception,
            RedirectAttributes redirectAttributes) {

        log.warn("CSRF validation failed: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute(
                "message",
                "Session expired, please log in again"
        );

        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class)
    public String genericExceptionHandler(Exception exception) {
        log.error("Unexpected error", exception);
        return "500";
    }
}
