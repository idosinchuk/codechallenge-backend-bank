package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.AccountPresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateAccountUseCase {

    private final AccountService accountService;

    @Transactional
    public void createAccount(AccountPresentation accountPresentation) {
        AccountDTO accountDTO = AccountDTO.of(accountPresentation);
        accountService.createAccount(accountDTO);
    }

}
