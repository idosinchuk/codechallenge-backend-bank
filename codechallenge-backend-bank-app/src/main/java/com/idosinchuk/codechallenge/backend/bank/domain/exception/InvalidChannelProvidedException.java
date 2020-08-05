package com.idosinchuk.codechallenge.backend.bank.domain.exception;

public class InvalidChannelProvidedException extends RuntimeException {
    public InvalidChannelProvidedException(String channel) {
        super("Invalid channel provided " + channel);
    }
}
