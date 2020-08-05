package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionStatusDTO {

    private String reference;
    private TransactionStatus status;
    private Double amount;
    private Double fee;

    public static TransactionStatusDTO ofTransactionNotStored(String reference) {

        return TransactionStatusDTO.builder()
                .reference(reference)
                .status(TransactionStatus.INVALID)
                .build();
    }

    public static TransactionStatusDTO of(TransactionEntity transactionEntity, String channel) throws ParseException {

        DateFormat dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
        Date transactionDate = dateFormat.parse(transactionEntity.getDate());

        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();

        if (DateUtils.isToday(transactionDate)) {
            transactionStatusDTO = transactionToday(transactionEntity, channel);
        } else if (DateUtils.isBeforeToday(transactionDate)) {
            transactionStatusDTO = transactionBeforeToday(transactionEntity, channel);
        } else if (DateUtils.isAfterToday(transactionDate)) {
            transactionStatusDTO = transactionAfterToday(transactionEntity, channel);
        }

        return transactionStatusDTO;
    }
    private static TransactionStatusDTO transactionToday(TransactionEntity transactionEntity, String channel) {

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.builder()
                .reference(transactionEntity.getId())
                .build();

        switch (ChannelType.getChannelType(channel)) {
            case CLIENT:
            case ATM:
                transactionStatusDTO.setStatus(TransactionStatus.PENDING);
                transactionStatusDTO.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL:
                transactionStatusDTO.setStatus(TransactionStatus.PENDING);
                transactionStatusDTO.setAmount(transactionEntity.getAmount());
                transactionStatusDTO.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusDTO;
    }

    private static TransactionStatusDTO transactionBeforeToday(TransactionEntity transactionEntity, String channel) {

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.builder()
                .reference(transactionEntity.getId())
                .build();

        switch (ChannelType.getChannelType(channel)) {
            case CLIENT:
            case ATM:
                transactionStatusDTO.setStatus(TransactionStatus.SETTLED);
                transactionStatusDTO.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL:
                transactionStatusDTO.setStatus(TransactionStatus.SETTLED);
                transactionStatusDTO.setAmount(transactionEntity.getAmount());
                transactionStatusDTO.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusDTO;
    }

    private static TransactionStatusDTO transactionAfterToday(TransactionEntity transactionEntity, String channel) {

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.builder()
                .reference(transactionEntity.getId())
                .build();

        switch (ChannelType.getChannelType(channel)) {
            case CLIENT:
                transactionStatusDTO.setStatus(TransactionStatus.FUTURE);
                transactionStatusDTO.setAmount(transactionEntity.getAmountToProcess());
                break;
            case ATM:
                transactionStatusDTO.setStatus(TransactionStatus.PENDING);
                transactionStatusDTO.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL:
                transactionStatusDTO.setStatus(TransactionStatus.FUTURE);
                transactionStatusDTO.setAmount(transactionEntity.getAmount());
                transactionStatusDTO.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusDTO;
    }

}
