package com.idosinchuk.codechallenge.backend.bank.infrastructure.adapter.repository;

import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Optional<TransactionEntity> find(String reference) {
        log.info("Find transaction in DB by reference {}", reference);
        return transactionJpaRepository.findById(reference);
    }

    @Override
    public List<TransactionEntity> findAll() {
        log.info("Find all transactions in DB");
        return transactionJpaRepository.findAll();
    }

}
