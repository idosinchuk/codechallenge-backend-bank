package com.idosinchuk.codechallenge.backend.bank.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountEntity {

    @Id
    private String id;

    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TransactionEntity> transactions;

    public static AccountEntity of(AccountDTO accountDTO) {

        AccountEntity accountEntity;

        try {
            accountEntity = AccountEntity.builder()
                    .id(accountDTO.getId())
                    .balance(accountDTO.getBalance())
                    .transactions(accountDTO.getTransactions().stream()
                            .map(TransactionEntity::of)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("There was an error creating AccountEntity from AccountDTO", e);
        }

        return accountEntity;
    }

}
