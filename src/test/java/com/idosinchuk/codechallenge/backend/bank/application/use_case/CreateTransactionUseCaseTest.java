package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import com.idosinchuk.codechallenge.backend.bank.application.service.impl.TransactionServiceImpl;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionUseCaseTest {

    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    AccountService accountService;

    private DateFormat dateFormat;

    public CreateTransactionUseCaseTest() {
    }

    @Before
    public void setUp() {
        this.createTransactionUseCase = new CreateTransactionUseCase(accountService);
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }

    @Test
    public void shouldCreateTransaction() {

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

        when(accountService.getAccount(transactionDTO.getAccountIban())).thenReturn(accountDTO);

        createTransactionUseCase.createTransaction(transactionDTO);

        verify(accountService, times(1)).getAccount(isA(String.class));
        verify(accountService, times(1)).saveAccount(any(AccountDTO.class), any(TransactionDTO.class));

    }

}
