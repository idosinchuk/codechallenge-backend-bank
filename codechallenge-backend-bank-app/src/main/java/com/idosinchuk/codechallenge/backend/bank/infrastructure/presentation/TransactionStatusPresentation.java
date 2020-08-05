package com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionStatusPresentation {

    @ApiModelProperty(value = "The transaction reference number.", example = "12345A", position = 1)
    private String reference;

    @ApiModelProperty(value = "The status of the transaction. It can be any of these values: PENDING, SETTLED, FUTURE, INVALID.", example = "PENDING", position = 2)
    private TransactionStatus status;

    @ApiModelProperty(value = "The amount of the transaction.", example = "193.38", position = 3)
    private Double amount;

    @ApiModelProperty(value = "The fee applied to the transaction.", example = "3.18", position = 4)
    private Double fee;

    public static TransactionStatusPresentation of(TransactionStatusDTO transactionStatusDTO) {

        String reference = transactionStatusDTO.getReference();
        TransactionStatus transactionStatus = transactionStatusDTO.getStatus();
        Double amount = transactionStatusDTO.getAmount();
        Double fee = transactionStatusDTO.getFee();

        return TransactionStatusPresentation.builder()
                .reference(reference)
                .status(transactionStatus)
                .amount(amount)
                .fee(fee)
                .build();

    }
}
