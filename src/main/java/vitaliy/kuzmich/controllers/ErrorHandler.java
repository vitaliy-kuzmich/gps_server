package vitaliy.kuzmich.controllers;

import vitaliy.kuzmich.excp.WrongFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data format violation")
    public String handleAllException(Exception ex) {
        return ex.getLocalizedMessage();

    }

    @ExceptionHandler({IllegalArgumentException.class, WrongFormatException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Data format violation")
    public String wrongFormat(Exception ex) {
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler({ Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Data format violation")
    public String wrong(Exception ex) {
        ex.printStackTrace();
        return ex.getLocalizedMessage();
    }
}
