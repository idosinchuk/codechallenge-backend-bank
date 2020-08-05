package com.idosinchuk.codechallenge.backend.bank.application.service;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;

public interface AccountService {

    AccountDTO getAccount(String accountIban);

    void updateAccount(AccountDTO accountDTO, TransactionDTO transactionDTO);

    void createAccount(AccountDTO accountDTO);

}
