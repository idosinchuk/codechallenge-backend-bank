package com.idosinchuk.codechallenge.backend.bank.application.dto;

import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class TransactionStatusDTOTest {

    private DateFormat dateFormat;

    private TransactionEntity transactionEntity;

    @Before
    public void setUp() {
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;

        transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .amount(193.38)
                .fee(3.18)
                .description("Restaurant payment")
                .account(AccountEntity.builder().id(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionTodayAndChannelIsClient() throws ParseException {

        String channel = ChannelType.CLIENT.toString();

        transactionEntity.setDate(dateFormat.format(new Date()));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.PENDING);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionTodayAndChannelIsAtm() throws ParseException {

        String channel = ChannelType.ATM.toString();

        transactionEntity.setDate(dateFormat.format(new Date()));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.PENDING);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionTodayAndChannelIsInternal() throws ParseException {

        String channel = ChannelType.INTERNAL.toString();

        transactionEntity.setDate(dateFormat.format(new Date()));

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.PENDING);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(transactionStatusDTO.getFee()).isEqualTo(transactionEntity.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionBeforeTodayAndChannelIsClient() throws ParseException {

        String channel = ChannelType.CLIENT.toString();

        transactionEntity.setDate(dateFormat.format(new Date(1220227200L * 1000)));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.SETTLED);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionBeforeTodayAndChannelIsAtm() throws ParseException {

        String channel = ChannelType.ATM.toString();

        transactionEntity.setDate(dateFormat.format(new Date(1220227200L * 1000)));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.SETTLED);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionBeforeTodayAndChannelIsInternal() throws ParseException {

        String channel = ChannelType.INTERNAL.toString();

        transactionEntity.setDate(dateFormat.format(new Date(1220227200L * 1000)));

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.SETTLED);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(transactionStatusDTO.getFee()).isEqualTo(transactionEntity.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionAfterTodayAndChannelIsClient() throws ParseException {

        String channel = ChannelType.CLIENT.toString();

        transactionEntity.setDate(dateFormat.format(new Date(2220227200L * 1000)));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.FUTURE);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionAfterTodayAndChannelIsAtm() throws ParseException {

        String channel = ChannelType.ATM.toString();

        transactionEntity.setDate(dateFormat.format(new Date(2220227200L * 1000)));

        Double amountToProcess = transactionEntity.getAmountToProcess();
        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.PENDING);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(amountToProcess);
        Assert.assertNull(transactionStatusDTO.getFee());
    }

    @Test
    public void shouldReturnTransactionStatusDTOWhenTransactionAfterTodayAndChannelIsInternal() throws ParseException {

        String channel = ChannelType.INTERNAL.toString();

        transactionEntity.setDate(dateFormat.format(new Date(2220227200L * 1000)));

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.of(transactionEntity, channel);

        Assertions.assertThat(transactionStatusDTO.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusDTO.getStatus()).isEqualTo(TransactionStatus.FUTURE);
        Assertions.assertThat(transactionStatusDTO.getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(transactionStatusDTO.getFee()).isEqualTo(transactionEntity.getFee());
    }
}
