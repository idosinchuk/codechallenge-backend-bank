package com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionPresentation {

    @ApiModelProperty(value = "The transaction unique reference number in our system. If not present, the system will generate one", example = "12345A", position = 1)
    private String reference;

    @ApiModelProperty(required = true, value = "The IBAN number of the account where the transaction has happened.", example = "ES6004871262385886192518", position = 2)
    @NotBlank(message = "Account iban is mandatory.")
    private String accountIban;

    @ApiModelProperty(value = "Date when the transaction took place.", example = "2019-07-16T16:55:42.000Z", position = 3)
    private String date;

    @ApiModelProperty(required = true, value = "If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account.", example = "193.38", position = 4)
    @NotNull(message = "Amount is mandatory")
    private Double amount;

    @ApiModelProperty(value = "Fee that will be deducted from the amount, regardless on the amount being positive or negative.", example = "3.18", position = 5)
    private double fee;

    @ApiModelProperty(value = "The description of the transaction.", example = "Restaurant payment", position = 6)
    private String description;

    public static TransactionPresentation of(TransactionDTO transactionDTO) {

        return TransactionPresentation.builder()
                .reference(transactionDTO.getReference())
                .accountIban(transactionDTO.getAccountIban())
                .date(transactionDTO.getDate())
                .amount(transactionDTO.getAmount())
                .fee(transactionDTO.getFee())
                .description(transactionDTO.getDescription())
                .build();
    }
}
