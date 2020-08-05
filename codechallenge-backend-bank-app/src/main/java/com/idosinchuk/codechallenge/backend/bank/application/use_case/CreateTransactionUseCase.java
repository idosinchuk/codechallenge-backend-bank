package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateTransactionUseCase {

    private final AccountService accountService;

    public void createTransaction(TransactionDTO transactionDTO) {
        transactionDTO.checkParameters();
        AccountDTO accountDTO = accountService.getAccount(transactionDTO.getAccountIban());
        accountService.updateAccount(accountDTO, transactionDTO);
    }

}
