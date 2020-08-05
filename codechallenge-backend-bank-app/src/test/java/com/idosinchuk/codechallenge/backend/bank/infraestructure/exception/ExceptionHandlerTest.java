package com.idosinchuk.codechallenge.backend.bank.infraestructure.exception;

import com.idosinchuk.codechallenge.backend.bank.domain.exception.AccountNotFoundException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InsufficientBalanceException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidChannelProvidedException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidSortingProvidedException;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.exception.ExceptionsHandler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerTest {

    @Test
    public void shouldReturn404NotFoundWhenThrownAccountNotFoundException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerAccountNotFoundException(new AccountNotFoundException("Account not found for IBAN ES6004871262385886192518"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturn400BadRequestWhenThrownInsufficientBalanceException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerInsufficientBalanceException(new InsufficientBalanceException("The account identified by IBAN ES6004871262385886192518 does not have enough balance."));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn400BadRequestWhenThrownInvalidChannelProvidedException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerInvalidChannelProvidedException(new InvalidChannelProvidedException("Invalid channel provided INVALID_CHANNEL"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturn400BadRequestWhenThrownInvalidSortingProvidedException() {
        ExceptionsHandler handler = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerInvalidSortingProvidedException(new InvalidSortingProvidedException("Invalid sorting provided INVALID_SORTING"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
