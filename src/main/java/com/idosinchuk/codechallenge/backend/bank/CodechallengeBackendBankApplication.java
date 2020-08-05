package com.idosinchuk.codechallenge.backend.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class CodechallengeBackendBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodechallengeBackendBankApplication.class, args);
    }

}