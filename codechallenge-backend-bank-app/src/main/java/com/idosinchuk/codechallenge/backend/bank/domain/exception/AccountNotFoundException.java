package com.idosinchuk.codechallenge.backend.bank.domain.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountIban) {
        super("Account identified with IBAN " + accountIban + "was not found");
    }

}
