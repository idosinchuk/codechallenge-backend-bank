package com.idosinchuk.codechallenge.backend.bank.domain.port.repository;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.AccountEntity;

import java.util.Optional;

public interface AccountRepository {

    void save(AccountEntity accountEntity);

    Optional<AccountEntity> find(String accountIban);

}
