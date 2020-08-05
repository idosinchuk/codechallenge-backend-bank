package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountDTO {

    private String id;
    private double balance;
    private List<TransactionDTO> transactions;

    public static AccountDTO of(AccountEntity accountEntity) {

        AccountDTO accountDTO;

        try {
            accountDTO = AccountDTO.builder()
                    .id(accountEntity.getId())
                    .balance(accountEntity.getBalance())
                    .transactions(accountEntity.getTransactions().stream()
                            .map(TransactionDTO::of)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("There was an error creating AccountDTO from AccountEntity", e);
        }

        return accountDTO;
    }

    public boolean haveEnoughBalance(double amountToProcess) {
        double finalAmount = this.balance + amountToProcess;
        return finalAmount >= 0;
    }

    public void updateBalance(double amountToProcess) {
        double newBalance = this.getBalance() + amountToProcess;
        this.setBalance(newBalance);
    }

    public void addTransaction(TransactionDTO transactionDTO) {
        this.transactions.add(transactionDTO);
    }

}
