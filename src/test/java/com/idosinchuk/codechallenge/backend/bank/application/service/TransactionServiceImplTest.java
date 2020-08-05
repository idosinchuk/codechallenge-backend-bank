package com.idosinchuk.codechallenge.backend.bank.application.service;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
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
public class TransactionServiceImplTest {

    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    TransactionRepository transactionRepository;

    DateFormat dateFormat;

    public TransactionServiceImplTest() {
    }

    @Before
    public void setUp() {
        this.transactionServiceImpl = new TransactionServiceImpl(transactionRepository);
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }

    @Test
    public void shouldGetTransactionStatus() throws ParseException {
        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";
        String channel = ChannelType.CLIENT.toString();

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

        when(transactionRepository.find(reference)).thenReturn(Optional.of(transactionEntity));

        TransactionStatusDTO transactionStatusDTO = transactionServiceImpl.getTransactionStatus(reference, channel);

        Assertions.assertThat(transactionStatusDTO).isNotNull();

        verify(transactionRepository, times(1)).find(reference);
    }

    @Test
    public void shouldReturnNotStoredTransaction() throws ParseException {
        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";
        String channel = ChannelType.CLIENT.toString();

        when(transactionRepository.find(reference)).thenReturn(Optional.empty());

        TransactionStatusDTO transactionStatusDTO = transactionServiceImpl.getTransactionStatus(reference, channel);

        Assertions.assertThat(transactionStatusDTO).isNotNull();
        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(reference);
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.INVALID);

        verify(transactionRepository, times(1)).find(reference);
    }

    @Test
    public void shouldGetAllTransactions() {
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

        when(transactionRepository.findAll()).thenReturn(transactionsList);

        transactionServiceImpl.getAllTransactions();

        verify(transactionRepository, times(1)).findAll();
    }

}
