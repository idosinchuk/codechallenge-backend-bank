package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.SortingType;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import com.idosinchuk.codechallenge.backend.bank.application.service.TransactionService;
import com.idosinchuk.codechallenge.backend.bank.application.utils.DateUtils;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidSortingProvidedException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchTransactionUseCaseTest {

    @Mock
    TransactionService transactionService;
    @Mock
    AccountService accountService;
    private SearchTransactionUseCase searchTransactionUseCase;
    private DateFormat dateFormat;

    public SearchTransactionUseCaseTest() {
    }

    @Before
    public void setUp() {
        this.searchTransactionUseCase = new SearchTransactionUseCase(transactionService, accountService);
        dateFormat = DateUtils.SIMPLE_DATE_FORMAT;
    }


    @Test
    public void shouldSearchTransactionWhenAccountIbanIsPresent() throws ParseException {
        String accountIban = "ES6004871262385886192518";
        String sorting = null;

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
                .balance(50.00)
                .transactions(transactionDTOS)
                .build();

        when(accountService.getAccount(accountIban)).thenReturn(accountDTO);

        List<TransactionDTO> transactionDTOList = searchTransactionUseCase.searchTransaction(accountIban, sorting);

        Assertions.assertThat(transactionDTOList).isNotNull();

        verify(transactionService, times(0)).getAllTransactions();
        verify(accountService, times(1)).getAccount(any(String.class));

    }

    @Test
    public void shouldSearchTransactionWhenAccountIbanIsNull() throws ParseException {

        String accountIban = null;
        String sorting = null;

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


        when(transactionService.getAllTransactions()).thenReturn(transactionDTOS);

        List<TransactionDTO> transactionDTOList = searchTransactionUseCase.searchTransaction(accountIban, sorting);

        Assertions.assertThat(transactionDTOList).isNotNull();

        verify(transactionService, times(1)).getAllTransactions();
        verify(accountService, times(0)).getAccount(any(String.class));

    }

    @Test
    public void shouldSearchTransactionWhenAccountIbanIsPresentAndSortingAscending() throws ParseException {
        String accountIban = "ES6004871262385886192518";
        String sorting = SortingType.ASCENDING.toString();

        TransactionDTO transactionDTO1 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(10.09)
                .fee(1.11)
                .description("Restaurant payment 1")
                .build();

        TransactionDTO transactionDTO2 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(15.38)
                .fee(2.12)
                .description("Restaurant payment 2")
                .build();

        TransactionDTO transactionDTO3 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(7.81)
                .fee(0.18)
                .description("Restaurant payment 3")
                .build();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        transactionDTOS.add(transactionDTO1);
        transactionDTOS.add(transactionDTO2);
        transactionDTOS.add(transactionDTO3);

        AccountDTO accountDTO = AccountDTO.builder()
                .id("ES6004871262385886192518")
                .balance(50.00)
                .transactions(transactionDTOS)
                .build();

        when(accountService.getAccount(accountIban)).thenReturn(accountDTO);

        List<TransactionDTO> transactionDTOList = searchTransactionUseCase.searchTransaction(accountIban, sorting);

        Assertions.assertThat(transactionDTOList).isNotNull();
        Assertions.assertThat(transactionDTOList.size()).isEqualTo(transactionDTOS.size());

        Assertions.assertThat(transactionDTOList)
                .containsExactlyInAnyOrderElementsOf(transactionDTOS.stream()
                        .sorted(Comparator.comparingDouble(TransactionDTO::getAmount))
                        .collect(Collectors.toList()));

        verify(accountService, times(1)).getAccount(any(String.class));
    }

    @Test
    public void shouldSearchTransactionWhenAccountIbanIsPresentAndSortingDescending() throws ParseException {
        String accountIban = "ES6004871262385886192518";
        String sorting = SortingType.DESCENDING.toString();

        TransactionDTO transactionDTO1 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(10.09)
                .fee(1.11)
                .description("Restaurant payment 1")
                .build();

        TransactionDTO transactionDTO2 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(15.38)
                .fee(2.12)
                .description("Restaurant payment 2")
                .build();

        TransactionDTO transactionDTO3 = TransactionDTO.builder()
                .reference(UUID.randomUUID().toString())
                .accountIban("ES6004871262385886192518")
                .date(dateFormat.format(new Date()))
                .amount(7.81)
                .fee(0.18)
                .description("Restaurant payment 3")
                .build();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        transactionDTOS.add(transactionDTO1);
        transactionDTOS.add(transactionDTO2);
        transactionDTOS.add(transactionDTO3);

        AccountDTO accountDTO = AccountDTO.builder()
                .id("ES6004871262385886192518")
                .balance(50.00)
                .transactions(transactionDTOS)
                .build();

        when(accountService.getAccount(accountIban)).thenReturn(accountDTO);

        List<TransactionDTO> transactionDTOList = searchTransactionUseCase.searchTransaction(accountIban, sorting);

        Assertions.assertThat(transactionDTOList).isNotNull();
        Assertions.assertThat(transactionDTOList.size()).isEqualTo(transactionDTOS.size());

        Assertions.assertThat(transactionDTOList)
                .containsExactlyInAnyOrderElementsOf(transactionDTOS.stream()
                        .sorted(Comparator.comparingDouble(TransactionDTO::getAmount))
                        .collect(Collectors.toList()));

        verify(accountService, times(1)).getAccount(any(String.class));
    }

    @Test
    public void shouldThrowInvalidSortingExceptionWhenAccountIbanIsPresentAndSortingIsInvalid() {

        String accountIban = "ES6004871262385886192518";
        String sorting = "INVALID_SORTING";

        org.junit.jupiter.api.Assertions.assertThrows(InvalidSortingProvidedException.class, () -> searchTransactionUseCase.searchTransaction(accountIban, sorting));

    }

}
