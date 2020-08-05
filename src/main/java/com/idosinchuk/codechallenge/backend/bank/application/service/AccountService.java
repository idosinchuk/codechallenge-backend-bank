package com.idosinchuk.codechallenge.backend.bank.application.service;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;

public interface AccountService {

    AccountDTO getAccount(String accountIban);

    void saveAccount(AccountDTO accountDTO, TransactionDTO transactionDTO);
}
