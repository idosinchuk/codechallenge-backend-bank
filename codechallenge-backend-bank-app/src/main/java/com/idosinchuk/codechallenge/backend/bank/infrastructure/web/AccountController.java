package com.idosinchuk.codechallenge.backend.bank.infrastructure.web;

import com.idosinchuk.codechallenge.backend.bank.application.use_case.CreateAccountUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.GetTransactionStatusUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.SearchTransactionUseCase;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.AccountPresentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Controller for Account operations.")
@RestController
@Slf4j
@RequestMapping(value = {"/api/account"})
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    @ApiOperation(value = "This endpoint will receive the account information and store it into the system.")
    @ResponseBody
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountPresentation accountPresentation) {

        log.info("Starts createAccount endpoint with accountIban {} and balance {}", accountPresentation.getAccountIban(), accountPresentation.getBalance());

        createAccountUseCase.createAccount(accountPresentation);

        log.info("Ends createAccount endpoint successfully");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
