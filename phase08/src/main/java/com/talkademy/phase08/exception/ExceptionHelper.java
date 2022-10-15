package com.talkademy.phase08.exception;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(Exception exception) {
        String message = LocalDateTime.now() + ": " + exception.getMessage();
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ExpiredException.class})
    public ResponseEntity<Object> handleExpiredException(Exception exception) {
        String message = LocalDateTime.now() + ": " + exception.getMessage();
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        String message = LocalDateTime.now() + ": " + exception.getMessage();
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UniqueViolationException.class})
    public ResponseEntity<Object> handleUniqueViolationException(Exception exception) {
        String message = LocalDateTime.now() + ": " + exception.getMessage();
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        String message = LocalDateTime.now() + ": " + exception.getMessage();
        logger.error(message);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
