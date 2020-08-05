package com.idosinchuk.codechallenge.backend.bank.domain.entity;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class AccountEntityTest {

    private TransactionEntity transactionEntity;
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

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        transactionDTOS.add(transactionDTO);

        AccountDTO accountDTO = AccountDTO.builder()
                .id("ES6004871262385886192518")
                .balance(50)
                .transactions(transactionDTOS)
                .build();

        AccountEntity accountEntity = AccountEntity.of(accountDTO);

        Assertions.assertThat(accountEntity.getId()).isEqualTo(accountDTO.getId());
        Assertions.assertThat(accountEntity.getBalance()).isEqualTo(accountDTO.getBalance());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getId()).isEqualTo(accountDTO.getTransactions().get(0).getReference());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getAccount().getId()).isEqualTo(accountDTO.getTransactions().get(0).getAccountIban());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getDate()).isEqualTo(accountDTO.getTransactions().get(0).getDate());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getAmount()).isEqualTo(accountDTO.getTransactions().get(0).getAmount());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getFee()).isEqualTo(accountDTO.getTransactions().get(0).getFee());
        Assertions.assertThat(accountEntity.getTransactions().get(0).getDescription()).isEqualTo(accountDTO.getTransactions().get(0).getDescription());
    }

    @Test
    public void shouldThrowRuntimeException() {
        AccountDTO accountDTO = AccountDTO.builder()
                .id("ES6004871262385886192518")
                .balance(50)
                .transactions(null)
                .build();

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> AccountEntity.of(accountDTO));
    }

}
