package com.idosinchuk.codechallenge.backend.bank.application.service.impl;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.AccountNotFoundException;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InsufficientBalanceException;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountDTO getAccount(String accountIban) {

        AccountEntity accountEntity = accountRepository.find(accountIban).orElse(null);

        if (!Objects.isNull(accountEntity)) {
            log.debug("Received account: {} from DB for accountIban: {}", accountEntity, accountIban);
            return AccountDTO.of(accountEntity);
        } else {
            log.error("Account identified with IBAN {} was not found", accountIban);
            throw new AccountNotFoundException(accountIban);
        }
    }

    public void updateAccount(AccountDTO accountDTO, TransactionDTO transactionDTO) {

        Double amountToProcess = transactionDTO.getAmountToProcess();

        if (accountDTO.haveEnoughBalance(amountToProcess)) {
            accountDTO.addTransaction(transactionDTO);
            accountDTO.updateBalance(amountToProcess);
            AccountEntity accountEntity = AccountEntity.of(accountDTO);
            accountRepository.save(accountEntity);
        } else {
            log.error("The account identified by IBAN {} does not have enough balance. The current balance in account is {} and the amount to process is {}", transactionDTO.getAccountIban(), accountDTO.getBalance(), amountToProcess);
            throw new InsufficientBalanceException(transactionDTO.getAccountIban());
        }
    }

    public void createAccount(AccountDTO accountDTO) {
        AccountEntity accountEntity = AccountEntity.of(accountDTO);
        accountRepository.save(accountEntity);
    }

}
