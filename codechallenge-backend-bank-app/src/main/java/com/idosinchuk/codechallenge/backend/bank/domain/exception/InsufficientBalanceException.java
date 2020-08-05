package com.idosinchuk.codechallenge.backend.bank.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String accountIban) {
        super("The account identified by IBAN" + accountIban + "does not have enough balance.");
    }
}
