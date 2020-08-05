package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.TransactionPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class TransactionDTOTest {

    private DateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }

    @Test
    public void shouldReturnTransactionDTO() {

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .date(dateFormat.format(new Date()))
                .amount(193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .account(AccountEntity.builder().id(UUID.randomUUID().toString())
                        .build())
                .build();

        TransactionDTO transactionDTO = TransactionDTO.of(transactionEntity);

        Assertions.assertThat(transactionDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionDTO.getAccountIban()).isEqualTo(transactionEntity.getAccount().getId());
        Assertions.assertThat(transactionDTO.getDate()).isEqualTo(transactionEntity.getDate());
        Assertions.assertThat(transactionDTO.getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(transactionDTO.getFee()).isEqualTo(transactionEntity.getFee());
        Assertions.assertThat(transactionDTO.getDescription()).isEqualTo(transactionEntity.getDescription());
    }

    @Test
    public void shouldGetAmountToProcessWhenAddBalance() {

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .date(dateFormat.format(new Date()))
                .amount(193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .account(AccountEntity.builder().id(UUID.randomUUID().toString())
                        .build())
                .build();

        TransactionDTO transactionDTO = TransactionDTO.of(transactionEntity);
        double amountToProcess = transactionDTO.getAmountToProcess();
        double expectedAmount = 193.38 - 3.18;

        Assertions.assertThat(amountToProcess).isEqualTo(expectedAmount);
    }

    @Test
    public void shouldGetAmountToProcessWhenDeductBalance() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .date(dateFormat.format(new Date()))
                .amount(-193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .account(AccountEntity.builder().id(UUID.randomUUID().toString())
                        .build())
                .build();

        TransactionDTO transactionDTO = TransactionDTO.of(transactionEntity);
        double amountToProcess = transactionDTO.getAmountToProcess();
        double expectedAmount = -193.38 - 3.18;

        Assertions.assertThat(amountToProcess).isEqualTo(expectedAmount);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenOfTransactionEntity() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .date(dateFormat.format(new Date()))
                .amount(-193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .account(null)
                .build();

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> TransactionDTO.of(transactionEntity));
    }

    @Test
    public void shouldThrowRuntimeExceptionOfTransactionPresentation() {
        TransactionPresentation transactionPresentation = null;
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> TransactionDTO.of(transactionPresentation));
    }


}
