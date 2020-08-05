package com.idosinchuk.codechallenge.backend.bank.domain.port.repository;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Optional<TransactionEntity> find(String reference);

    List<TransactionEntity> findAll();

}
