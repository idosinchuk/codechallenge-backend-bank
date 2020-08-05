package com.idosinchuk.codechallenge.backend.bank.infrastructure.adapter.repository;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<AccountEntity> find(String accountIban) {
        log.debug("Find account in DB by accountIban {}", accountIban);
        return accountJpaRepository.findById(accountIban);
    }

    @Override
    public void save(AccountEntity accountEntity) {
        log.debug("Save account in DB {}", accountEntity.toString());
        accountJpaRepository.save(accountEntity);
    }

}
