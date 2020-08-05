package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.idosinchuk.codechallenge.backend.bank.application.service.TransactionService;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidChannelProvidedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionStatusUseCaseTest {

    private GetTransactionStatusUseCase getTransactionStatusUseCase;

    @Mock
    TransactionService transactionService;

    public GetTransactionStatusUseCaseTest() {
    }

    @Before
    public void setUp() {
        this.getTransactionStatusUseCase = new GetTransactionStatusUseCase(transactionService);
    }

    @Test
    public void shouldGetTransactionStatus() throws ParseException {

        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";
        String channel = ChannelType.CLIENT.toString();

        TransactionStatusDTO transactionStatusDTO = TransactionStatusDTO.builder()
                .reference(reference)
                .status(TransactionStatus.PENDING)
                .amount(50.00)
                .fee(2.00).build();

        when(transactionService.getTransactionStatus(reference, channel)).thenReturn(transactionStatusDTO);

        getTransactionStatusUseCase.getTransactionStatus(reference, channel);

        verify(transactionService, times(1)).getTransactionStatus(any(String.class), any(String.class));

    }

    @Test
    public void shouldThrowInvalidChannelProvidedException() {

        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";
        String channel = "INVALID_CHANNEL";

        Assertions.assertThrows(InvalidChannelProvidedException.class, () -> getTransactionStatusUseCase.getTransactionStatus(reference, channel));
    }

}
