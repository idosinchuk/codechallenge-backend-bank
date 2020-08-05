package com.idosinchuk.codechallenge.backend.bank.infrastructure.exception;

import com.idosinchuk.codechallenge.backend.bank.domain.exception.AccountNotFoundException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InsufficientBalanceException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidChannelProvidedException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidSortingProvidedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<Object> handlerAccountNotFoundException(AccountNotFoundException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<Object> handlerInsufficientBalanceException(InsufficientBalanceException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = InvalidChannelProvidedException.class)
    public ResponseEntity<Object> handlerInvalidChannelProvidedException(InvalidChannelProvidedException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = InvalidSortingProvidedException.class)
    public ResponseEntity<Object> handlerInvalidSortingProvidedException(InvalidSortingProvidedException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
