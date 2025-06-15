package com.wk.advice;

import com.wk.model.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class MainControllerAdvice {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundException(EntityNotFoundException exception,
                                                      ServletWebRequest request) {
        return handle(
            exception,
            request,
            NOT_FOUND,
            exception.getMessage()
        );
    }

    private ResponseEntity<ErrorDto> handle(Throwable exception,
                                            ServletWebRequest request,
                                            HttpStatus status,
                                            String message) {
        log(exception, request);
        return ResponseEntity
            .status(status)
            .body(
                ErrorDto
                    .builder()
                    .code(null)
                    .timestamp(new Date())
                    .status(status.value())
                    .reason(status.getReasonPhrase())
                    .message(
                        ofNullable(message).orElse("Opps something went wrong")
                    )
                    .build()
            );
    }

    private void log(Throwable exception,
                     WebRequest request) {
        log.warn(
            "Resolved exception {} for request {}",
            exception.getMessage(),
            request.getDescription(true)
        );
    }

}
