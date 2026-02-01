package com.webtrak.exception;

import com.webtrak.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(
            UserAlreadyExistsException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(
            UserNotFoundException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(
            InvalidCredentialsException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), 401),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<ErrorResponseDto> handleInactiveUser(
            InactiveUserException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), 403),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(
            Exception ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponseDto("Internal server error", 500),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
