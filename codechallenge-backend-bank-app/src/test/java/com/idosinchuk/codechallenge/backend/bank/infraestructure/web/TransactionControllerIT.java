package com.idosinchuk.codechallenge.backend.bank.infraestructure.web;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.TransactionStatus;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.CreateTransactionUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.GetTransactionStatusUseCase;
import com.idosinchuk.codechallenge.backend.bank.application.use_case.SearchTransactionUseCase;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.TransactionPresentation;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.web.TransactionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = TransactionController.class)
public class TransactionControllerIT {

    private String createTransactionEndpoint;
    private String searchTransactionEndpoint;
    private String getTransactionStatus;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTransactionUseCase createTransactionUseCase;

    @MockBean
    private SearchTransactionUseCase searchTransactionUseCase;

    @MockBean
    private GetTransactionStatusUseCase getTransactionStatusUseCase;

    @Before
    public void setUp() throws ParseException {
        createTransactionEndpoint = "http://localhost:8080/api/transaction/create";
        searchTransactionEndpoint = "http://localhost:8080/api/transaction/find";
        getTransactionStatus = "http://localhost:8080/api/transaction/status";

        Mockito.when(searchTransactionUseCase.searchTransaction(any(String.class), any(String.class))).thenReturn(Collections.singletonList(TransactionDTO.builder()
                .reference("d23afa94-580d-4c8c-a89f-942758423c63")
                .accountIban("ES6004871262385886192518")
                .date("2019-07-01T16:55:42.000Z")
                .amount(21.11)
                .fee(2.07)
                .description("Restaurant payment")
                .build()));

        Mockito.when(getTransactionStatusUseCase.getTransactionStatus(any(String.class), any(String.class))).thenReturn(TransactionStatusDTO.builder()
                .reference("d23afa94-580d-4c8c-a89f-942758423c63")
                .amount(11.55)
                .fee(0.66)
                .status(TransactionStatus.SETTLED)
                .build());
    }

    @Test
    public void createTransactionShouldReturn201Created() throws Exception {

        TransactionPresentation transactionPresentation = TransactionPresentation.builder()
                .reference("d23afa94-580d-4c8c-a89f-942758423c63")
                .accountIban("ES6004871262385886192518")
                .date("2019-07-01T16:55:42.000Z")
                .amount(20.00)
                .fee(1.00)
                .description("Restaurant payment")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(transactionPresentation)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createTransactionShouldReturn400BadRequestWhenAccountIbanIsNull() throws Exception {

        TransactionPresentation transactionPresentation = TransactionPresentation.builder()
                .reference("d23afa94-580d-4c8c-a89f-942758423c63")
                .date("2019-07-01T16:55:42.000Z")
                .amount(20.00)
                .fee(1.00)
                .description("Restaurant payment")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(transactionPresentation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransactionShouldReturn400BadRequestWhenAmountIsNull() throws Exception {

        TransactionPresentation transactionPresentation = TransactionPresentation.builder()
                .reference("d23afa94-580d-4c8c-a89f-942758423c63")
                .date("2019-07-01T16:55:42.000Z")
                .fee(1.00)
                .description("Restaurant payment")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(transactionPresentation)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchTransactionShouldReturn200Ok() throws Exception {

        mockMvc.perform(get(searchTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void searchTransactionStatusShouldReturn200OkWhenAccountIbanIsPresent() throws Exception {

        mockMvc.perform(get(searchTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountIban", "ES6004871262385886192518"))
                .andExpect(jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void searchTransactionStatusShouldReturn200OkWhenSortingIsPresent() throws Exception {

        mockMvc.perform(get(searchTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("sorting", "ascending"))
                .andExpect(jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void searchTransactionStatusShouldReturn200OkWhenAccountIbanAndSortingArePresent() throws Exception {

        mockMvc.perform(get(searchTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountIban", "ES6004871262385886192518")
                .param("sorting", "ascending"))
                .andExpect(jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void getTransactionStatusShouldReturn200OkWhenReferenceAndChannelArePresent() throws Exception {

        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";
        String channel = ChannelType.CLIENT.toString();

        mockMvc.perform(get(getTransactionStatus)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("reference", reference)
                .param("channel", channel))
                .andExpect(jsonPath("$").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void getTransactionStatusShouldReturns400BadRequestWhenChannelIsNull() throws Exception {

        String reference = "d23afa94-580d-4c8c-a89f-942758423c63";

        mockMvc.perform(get(getTransactionStatus)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("reference", reference))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTransactionStatusShouldReturn400BadRequestWhenReferenceIsNull() throws Exception {

        String channel = ChannelType.CLIENT.toString();

        mockMvc.perform(get(getTransactionStatus)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("channel", channel))
                .andExpect(status().isBadRequest());
    }

}
