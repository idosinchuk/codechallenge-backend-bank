package com.idosinchuk.codechallenge.backend.bank.application.service.impl;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.service.TransactionService;
import com.idosinchuk.codechallenge.backend.bank.domain.entity.TransactionEntity;
import com.idosinchuk.codechallenge.backend.bank.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionStatusDTO getTransactionStatus(String reference, String channel) throws ParseException {

        TransactionEntity transactionEntity = transactionRepository.find(reference).orElse(null);

        if (!Objects.isNull(transactionEntity)) {
            log.debug("Received transaction: {} from DB for reference: {}", transactionEntity, reference);
            return TransactionStatusDTO.of(transactionEntity, channel);
        } else {
            log.error("Transaction not found for reference {}", reference);
            return TransactionStatusDTO.ofTransactionNotStored(reference);
        }
    }

    public List<TransactionDTO> getAllTransactions() {

        List<TransactionEntity> transactionEntities = transactionRepository.findAll();

        List<TransactionDTO> transactionResponses = transactionEntities.stream()
                .map(TransactionDTO::of)
                .collect(Collectors.toList());

        return transactionResponses;
    }

}
