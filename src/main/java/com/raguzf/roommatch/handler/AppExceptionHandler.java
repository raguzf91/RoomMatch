package com.raguzf.roommatch.handler;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;

/**
 * Global exception handler that intercepts and handles exceptions
 * provides appropriate HTTP responses and error details
 * @author raguzf
 */
@RestControllerAdvice
public class AppExceptionHandler {
    
    /**
     * Handles LockedException thrown when an account is locked.
     *
     * @param e the LockedException
     * @return a ResponseEntity with an UNAUTHORIZED status and details of the exception
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> lockedException(LockedException e) {

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ExceptionResponse.builder()
                                .errorCode(ErrorCodes.ACCOUNT_LOCKED.getCode())
                                .exceptionDescription(ErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(e.getMessage())
                                .build()
            );
    }

    /**
     * Handles DisabledException thrown when an account is disabled.
     *
     * @param e the DisabledException
     * @return a ResponseEntity with an UNAUTHORIZED status and details of the exception
     */

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException(DisabledException e) {

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ExceptionResponse.builder()
                                .errorCode(ErrorCodes.ACCOUNT_DISABLED.getCode())
                                .exceptionDescription(ErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(e.getMessage())
                                .build()
            );
    }

    /**
     * Handles BadCredentialsException thrown when invalid credentials are provided.
     *
     * @param e the BadCredentialsException
     * @return a ResponseEntity with an UNAUTHORIZED status and details of the exception
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsxception(BadCredentialsException e) {

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                ExceptionResponse.builder()
                                .errorCode(ErrorCodes.BAD_CREDENTIALS.getCode())
                                .exceptionDescription(ErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(e.getMessage())
                                .build()
            );
    }

    /**
     * Handles MessagingException thrown during email sending failures.
     *
     * @param e the MessagingException
     * @return a ResponseEntity with an INTERNAL_SERVER_ERROR status and details of the exception
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> messagingException(MessagingException e) {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                                .error(e.getMessage())
                                .build()
            );
    }

     /**
     * Handles MethodArgumentNotValidException thrown when method arguments are not valid.
     *
     * @param e the MethodArgumentNotValidException
     * @return a ResponseEntity with a BAD_REQUEST status and details of the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentException(MethodArgumentNotValidException e) {
        Set<String> errors = new HashSet<>();
        e.getBindingResult().getAllErrors()
                            .forEach(error -> {
                                var errorMessage = error.getDefaultMessage();
                                errors.add(errorMessage);

                            }); 
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse.builder()
                                .validationErrors(errors)
                                .error(e.getMessage())
                                .build()
            );
    }

    /**
     * Handles all other exceptions not specifically handled by other methods.
     *
     * @param e the Exception
     * @return a ResponseEntity with an INTERNAL_SERVER_ERROR status and details of the exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> generalException (Exception e) {
        e.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                                .exceptionDescription("Internal error")
                                .error(e.getMessage())
                                .build()
            );
    }
}
