package com.api.distri.controllers;

import com.api.distri.exceptions.BaseException;
import com.api.distri.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptions {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptions.class);

    @ExceptionHandler(NotFoundException.class)
    public String handle404Error(NotFoundException e, Model model) {
        logger.error("404 Error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "404";
    }

    @ExceptionHandler(Exception.class)
    public String handle500Error(Exception e, Model model) {
        logger.error("500 Error: {}", e.getMessage(), e);
        model.addAttribute("errorMessage", e.getMessage());
        return "500";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle400Error(HttpServletRequest request, IllegalArgumentException e, Model model) {
        logger.error("400 Error at {}: {}", request.getRequestURI(), e.getMessage());
        model.addAttribute("errorMessage", "La solicitud no es válida: " + e.getMessage());
        return "400";
    }
}
