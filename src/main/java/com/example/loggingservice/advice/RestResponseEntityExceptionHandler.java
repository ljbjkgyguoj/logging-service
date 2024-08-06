package com.example.loggingservice.advice;

import com.example.loggingservice.exception.BadRequestException;
import com.example.loggingservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Класс для обработки ошибок.
 */
@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    /**
     * Метод для обработки исключения: {@link NotFoundException}, http-status 404.
     *
     * @param ex ошибка
     * @return объект ResponseEntity
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Метод для обработки исключения: {@link BadRequestException}, http-status 400.
     *
     * @param ex ошибка
     * @return объект ResponseEntity
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Метод для обработки исключений {@link RuntimeException}, http-status 500.
     *
     * @param ex ошибка
     * @return объект ResponseEntity
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Неизвестная ошибка.");
    }
}
