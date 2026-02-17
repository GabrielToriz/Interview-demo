package com.teksystems.gdit.demo.aspects;

import com.teksystems.gdit.demo.model.request.InputError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    log.error("Validation exception occurred: {}", ex.getMessage());
    List<InputError> inputErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    InputError.builder()
                        .field(fieldError.getField())
                        //.rejectedValue(fieldError.getRejectedValue())
                        .message(fieldError.getDefaultMessage())
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.status(BAD_REQUEST).body(inputErrors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.error("HttpMessageNotReadableException occurred: {} ", ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST).build();
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
    log.error("NoResourceFoundException occurred: {} ", ex.getMessage());
    return ResponseEntity.status(NOT_FOUND).build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    log.error("Exception occurred: {} ", ex.getMessage());
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
  }
}
