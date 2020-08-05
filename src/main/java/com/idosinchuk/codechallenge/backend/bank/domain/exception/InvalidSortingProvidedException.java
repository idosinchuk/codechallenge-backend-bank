package com.idosinchuk.codechallenge.backend.bank.domain.exception;

public class InvalidSortingProvidedException extends RuntimeException {

    public InvalidSortingProvidedException(String sorting) {
        super("Invalid sorting provided {} " + sorting);
    }

}
