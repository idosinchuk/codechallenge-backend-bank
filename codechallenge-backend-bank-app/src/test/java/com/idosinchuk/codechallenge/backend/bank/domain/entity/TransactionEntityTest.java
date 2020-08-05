package com.idosinchuk.codechallenge.backend.bank.domain.entity;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class TransactionEntityTest {

    private DateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }

    @Test
    public void shouldReturnTransactionEntity() {

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .build();

        TransactionEntity transactionEntity = TransactionEntity.of(transactionDTO);

        Assertions.assertThat(transactionEntity.getId()).isEqualTo(transactionDTO.getReference());
        Assertions.assertThat(transactionEntity.getAccount().getId()).isEqualTo(transactionDTO.getAccountIban());
        Assertions.assertThat(transactionEntity.getDate()).isEqualTo(transactionDTO.getDate());
        Assertions.assertThat(transactionEntity.getAmount()).isEqualTo(transactionDTO.getAmount());
        Assertions.assertThat(transactionEntity.getFee()).isEqualTo(transactionDTO.getFee());
        Assertions.assertThat(transactionEntity.getDescription()).isEqualTo(transactionDTO.getDescription());
    }

    @Test
    public void shouldGetAmountToProcessWhenAddBalance() {

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .build();

        TransactionEntity transactionEntity = TransactionEntity.of(transactionDTO);

        Double amountToProcess = transactionEntity.getAmountToProcess();
        Double expectedAmount = 193.38 - 3.18;

        Assertions.assertThat(amountToProcess).isEqualTo(expectedAmount);
    }

    @Test
    public void shouldGetAmountToProcessWhenDeductBalance() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(-193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .build();

        TransactionEntity transactionEntity = TransactionEntity.of(transactionDTO);

        Double amountToProcess = transactionEntity.getAmountToProcess();
        Double expectedAmount = -193.38 - 3.18;

        Assertions.assertThat(amountToProcess).isEqualTo(expectedAmount);
    }

    @Test
    public void shouldThrowRuntimeException() {
        TransactionDTO transactionDTO = null;
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> TransactionEntity.of(transactionDTO));
    }

}
