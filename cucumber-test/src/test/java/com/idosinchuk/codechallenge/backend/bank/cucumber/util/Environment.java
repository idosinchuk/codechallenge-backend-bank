package com.idosinchuk.codechallenge.backend.bank.cucumber.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@Slf4j
@ToString
public enum Environment {

    LOCAL("http://localhost:8080"),

    DEV("https://dev-example:8080"),

    PRE("https://pre-example:8080"),

    QA("https://qa-example:8080"),

    CUSTOM(System.getenv("EXAMPLE-CUSTOM-URL"));

    private final String transactionEndpoint;

    public static Environment getCurrent() {
        final String ENV_VARIABLE = "ENVIRONMENT";
        Environment environment = Optional.of(Environment.valueOf(System.getenv(ENV_VARIABLE) != null ? System.getenv(ENV_VARIABLE) : CUSTOM.name())).orElse(Environment.valueOf("LOCAL"));
        log.info("The test suite is prepared to run on {} environment with endpoints {}", environment);
        return environment;
    }

}
