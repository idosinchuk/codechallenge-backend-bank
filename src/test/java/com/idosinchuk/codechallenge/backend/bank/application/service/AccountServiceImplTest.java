package com.idosinchuk.codechallenge.backend.bank.application.service;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.impl.AccountServiceImpl;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.AccountNotFoundException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InsufficientBalanceException;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private AccountServiceImpl accountServiceImpl;

    @Mock
    AccountRepository accountRepository;

    private DateFormat dateFormat;

    @Before
    public void setUp() {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository);
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }

    @Test
    public void shouldGetAccount() {
        String accountIban = "ES6004871262385886192518";

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

        when(accountRepository.find(accountIban)).thenReturn(Optional.of(accountEntity));

        AccountDTO accountDTO = accountServiceImpl.getAccount(accountIban);

        org.assertj.core.api.Assertions.assertThat(accountDTO).isNotNull();

        verify(accountRepository, times(1)).find(accountIban);
    }

    @Test
    public void shouldThrowAccountNotFoundException() {
        org.junit.jupiter.api.Assertions.assertThrows(AccountNotFoundException.class, () -> {
            String accountIban = "ES6004871262385886192518";

            when(accountRepository.find(accountIban)).thenReturn(Optional.empty());
            accountServiceImpl.getAccount(accountIban);
        });
    }

    @Test
    public void shouldSaveAccount() {
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
                .balance(900)
                .transactions(transactionDTOS)
                .build();

        accountServiceImpl.saveAccount(accountDTO, transactionDTO);

        verify(accountRepository, times(1)).save(isA(AccountEntity.class));
    }

    @Test
    public void shouldThrowInsufficientBalanceException() {

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(-193.38)
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

        org.junit.jupiter.api.Assertions.assertThrows(InsufficientBalanceException.class, () -> accountServiceImpl.saveAccount(accountDTO, transactionDTO));
    }

}
