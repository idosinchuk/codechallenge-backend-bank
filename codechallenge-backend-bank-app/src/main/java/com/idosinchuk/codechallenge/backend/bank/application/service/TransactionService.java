package com.idosinchuk.codechallenge.backend.bank.application.service;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;

import java.text.ParseException;
import java.util.List;

public interface TransactionService {

    TransactionStatusDTO getTransactionStatus(String reference, String channel) throws ParseException;

    List<TransactionDTO> getAllTransactions() throws ParseException;
}
