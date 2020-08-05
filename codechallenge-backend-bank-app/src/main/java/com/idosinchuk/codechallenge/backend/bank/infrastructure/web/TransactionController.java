package com.idosinchuk.codechallenge.backend.bank.infrastructure.web;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.CreateTransactionUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.GetTransactionStatusUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.SearchTransactionUseCase;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.TransactionPresentation;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.TransactionStatusPresentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Controller for Transaction operations.")
@RestController
@Slf4j
@RequestMapping(value = {"/api/transaction"})
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    private final GetTransactionStatusUseCase getTransactionStatusUseCase;

    private final SearchTransactionUseCase searchTransactionUseCase;

    @ApiOperation(value = "This endpoint will receive the transaction information and store it into the system.")
    @ResponseBody
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionPresentation transactionPresentation) {

        log.info("Starts createTransaction endpoint with transactionPresentation: {}", transactionPresentation);
        TransactionDTO transactionDTO = TransactionDTO.of(transactionPresentation);

        createTransactionUseCase.createTransaction(transactionDTO);

        log.info("Ends createTransaction endpoint successfully");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @ApiOperation(value = "This endpoint, based on the payload and some business rules, will return the status and additional information for a specific transaction.")
    @ResponseBody
    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatusPresentation> getTransactionStatus(@RequestParam() String reference,
                                                                              @RequestParam() String channel) throws ParseException {

        log.info("Starts getTransaction endpoint with reference {} and channel {}", reference, channel);

        TransactionStatusDTO transactionStatusDTO = getTransactionStatusUseCase.getTransactionStatus(reference, channel);

        TransactionStatusPresentation transactionStatusPresentation = TransactionStatusPresentation.of(transactionStatusDTO);

        log.info("Ends getTransactionStatus endpoint successfully: {}", transactionStatusPresentation);
        return ResponseEntity.ok().body(transactionStatusPresentation);

    }

    @ApiOperation(value = "This endpoint, based on the payload and some business rules, will return the status and additional information for a specific transaction.")
    @ResponseBody
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionPresentation>> searchTransaction(@RequestParam(required = false) String accountIban,
                                                                           @RequestParam(required = false) String sorting) throws ParseException {

        log.info("Starts searchTransaction endpoint with accountIban {} and sorting {}", accountIban, sorting);

        List<TransactionDTO> transactionDTOS = searchTransactionUseCase.searchTransaction(accountIban, sorting);

        List<TransactionPresentation> transactionPresentation = transactionDTOS.stream()
                .map(TransactionPresentation::of)
                .collect(Collectors.toList());

        log.info("Ends searchTransaction endpoint successfully: {}", transactionPresentation);
        return ResponseEntity.ok().body(transactionPresentation);

    }


}
