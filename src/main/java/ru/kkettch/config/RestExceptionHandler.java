package ru.kkettch.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.kkettch.dataTransferObject.ErrorDto;
import ru.kkettch.exception.Exception;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorDto.builder().message(ex.getMessage()).build());
    }
}
