package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.AccountPresentation;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountDTO {

    private String id;
    private Double balance;
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

    public static AccountDTO of(AccountPresentation accountPresentation) {

        AccountDTO accountDTO;

        try {
            accountDTO = AccountDTO.builder()
                    .id(accountPresentation.getAccountIban())
                    .balance(accountPresentation.getBalance())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("There was an error creating AccountDTO from AccountPresentation", e);
        }

        return accountDTO;
    }

    public boolean haveEnoughBalance(Double amountToProcess) {
        Double finalAmount = this.balance + amountToProcess;
        return finalAmount >= 0;
    }

    public void updateBalance(Double amountToProcess) {
        Double newBalance = this.getBalance() + amountToProcess;
        this.setBalance(newBalance);
    }

    public void addTransaction(TransactionDTO transactionDTO) {
        this.transactions.add(transactionDTO);
    }

}
