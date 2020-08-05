package com.idosinchuk.codechallenge.backend.bank.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private AccountEntity account;

    private String date;

    private double amount;

    private double fee;

    private String description;

    public static TransactionEntity of(TransactionDTO transactionDTO) {

        TransactionEntity transactionEntity;

        try {
            AccountEntity accountEntity = AccountEntity.builder()
                    .id(transactionDTO.getAccountIban())
                    .build();

            transactionEntity = TransactionEntity.builder()
                    .id(transactionDTO.getReference())
                    .account(accountEntity)
                    .date(transactionDTO.getDate())
                    .amount(transactionDTO.getAmount())
                    .fee(transactionDTO.getFee())
                    .description(transactionDTO.getDescription())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("There was an error creating TransactionEntity from TransactionDTO", e);
        }
        return transactionEntity;
    }

    public double getAmountToProcess() {
        return this.amount - this.fee;
    }

}
