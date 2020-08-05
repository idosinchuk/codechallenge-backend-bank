package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateTransactionUseCase {

    private final AccountService accountService;

    @Transactional
    public void createTransaction(TransactionDTO transactionDTO) {
        AccountDTO accountDTO = accountService.getAccount(transactionDTO.getAccountIban());
        transactionDTO.checkParameters();
        accountService.saveAccount(accountDTO, transactionDTO);
    }

}
