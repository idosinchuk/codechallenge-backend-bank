package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
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
public class AccountDTOTest {

    private AccountDTO accountDTO;
    private DateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;

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

        this.accountDTO = accountDTO;
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

        List<TransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(transactionEntity);

        AccountEntity accountEntity = AccountEntity.builder()
                .id("ES6004871262385886192518")
                .balance(900)
                .transactions(transactionsList)
                .build();

        AccountDTO accountDTO = AccountDTO.of(accountEntity);

        Assertions.assertThat(accountDTO.getId()).isEqualTo(accountEntity.getId());
        Assertions.assertThat(accountDTO.getBalance()).isEqualTo(accountEntity.getBalance());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getAccountIban()).isEqualTo(transactionEntity.getAccount().getId());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getDate()).isEqualTo(transactionEntity.getDate());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getFee()).isEqualTo(transactionEntity.getFee());
        Assertions.assertThat(accountDTO.getTransactions().get(0).getDescription()).isEqualTo(transactionEntity.getDescription());

    }

    @Test
    public void shouldReturnHaveEnoughBalance() {

        double amountToProcess = -40.00;

        boolean haveEnoughBalance = accountDTO.haveEnoughBalance(amountToProcess);

        Assertions.assertThat(haveEnoughBalance).isTrue();
    }

    @Test
    public void shouldReturnNotHaveEnoughBalance() {

        double amountToProcess = -60.00;

        boolean haveEnoughBalance = accountDTO.haveEnoughBalance(amountToProcess);

        Assertions.assertThat(haveEnoughBalance).isFalse();
    }

    @Test
    public void shouldUpdateBalance() {

        double lastBalance = accountDTO.getBalance();
        double amountToProcess = 5.00;

        accountDTO.updateBalance(amountToProcess);

        Assertions.assertThat(accountDTO.getBalance()).isEqualTo(lastBalance + amountToProcess);
    }

    @Test
    public void shouldAddTransaction() {

        List<TransactionDTO> transactionDTOS = accountDTO.getTransactions();
        int sizeTransactionsList = transactionDTOS.size();

        accountDTO.addTransaction(transactionDTOS.get(0));

        Assertions.assertThat(accountDTO.getTransactions().size()).isEqualTo(sizeTransactionsList + 1);
    }

    @Test
    public void shouldThrowRuntimeException() {
        AccountEntity accountEntity = AccountEntity.builder()
                .id("ES6004871262385886192518")
                .balance(900)
                .transactions(null)
                .build();

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> AccountDTO.of(accountEntity));
    }
}
