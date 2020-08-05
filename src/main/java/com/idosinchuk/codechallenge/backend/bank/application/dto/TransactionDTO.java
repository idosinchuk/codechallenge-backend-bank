package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.TransactionPresentation;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionDTO {

    private String reference;
    private String accountIban;
    private String date;
    private double amount;
    private double fee;
    private String description;

    public static TransactionDTO of(TransactionPresentation transactionPresentation) {

        TransactionDTO transactionDTO;

        try {
            transactionDTO = TransactionDTO.builder()
                    .reference(transactionPresentation.getReference())
                    .accountIban(transactionPresentation.getAccountIban())
                    .date(transactionPresentation.getDate())
                    .amount(transactionPresentation.getAmount())
                    .fee(transactionPresentation.getFee())
                    .description(transactionPresentation.getDescription())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("There was an error creating TransactionDTO from TransactionPresentation", e);
        }

        return transactionDTO;
    }

    public static TransactionDTO of(TransactionEntity transactionEntity) {

        TransactionDTO transactionDTO;

        try {
            transactionDTO = TransactionDTO.builder()
                    .reference(transactionEntity.getId())
                    .accountIban(transactionEntity.getAccount().getId())
                    .date(transactionEntity.getDate())
                    .amount(transactionEntity.getAmount())
                    .fee(transactionEntity.getFee())
                    .description(transactionEntity.getDescription())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("There was an error creating TransactionDTO from TransactionEntity", e);
        }

        return transactionDTO;
    }

    public void checkParameters() {
        this.reference = Objects.isNull(this.reference) || reference.isEmpty() ? UUID.randomUUID().toString() : this.reference;
        SimpleDateFormat formatter = DateUtils.SIMPLE_DATE_FORMAT;
        this.date = Objects.isNull(this.date) ? formatter.format(new Date()) : this.date;
    }

    public double getAmountToProcess() {
        return this.amount - this.fee;
    }
}
