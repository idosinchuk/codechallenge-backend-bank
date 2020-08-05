package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.AccountDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.SortingType;
import com.idosinchuk.codechallenge.backend.bank.application.service.AccountService;
import com.idosinchuk.codechallenge.backend.bank.application.service.TransactionService;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidSortingProvidedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchTransactionUseCase {

    private final TransactionService transactionService;

    private final AccountService accountService;

    public List<TransactionDTO> searchTransaction(String accountIban, String sorting) throws ParseException {

        if (sorting != null) {
            boolean validSorting = isValidSorting(sorting);
            if (!validSorting) {
                log.error("Invalid sorting provided {}", sorting);
                throw new InvalidSortingProvidedException(sorting);
            }
        }

        List<TransactionDTO> transactionResponses;

        if (Objects.isNull(accountIban)) {
            log.info("The accountIban is not provided, try to obtain all transactions");
            transactionResponses = transactionService.getAllTransactions();

        } else {
            AccountDTO accountDTO = accountService.getAccount(accountIban);
            transactionResponses = accountDTO.getTransactions();
            log.info("There are {} transactions for the accountIban {}", transactionResponses.size(), accountIban);
        }

        if (sorting != null) {
            return sortTransactions(transactionResponses, sorting);
        }

        return transactionResponses;
    }

    private List<TransactionDTO> sortTransactions(List<TransactionDTO> transactionResponses, String sorting) {

        switch (SortingType.getSortingType(sorting)) {
            case ASCENDING:
                transactionResponses.stream()
                        .sorted(Comparator.comparingDouble(TransactionDTO::getAmount))
                        .collect(Collectors.toList());
                break;
            case DESCENDING:
                transactionResponses.stream()
                        .sorted(Comparator.comparingDouble(TransactionDTO::getAmount).reversed())
                        .collect(Collectors.toList());
                break;
        }
        return transactionResponses;
    }

    private static boolean isValidSorting(String channel) {
        return channel.equals(SortingType.ASCENDING.toString()) || channel.equals(SortingType.DESCENDING.toString());
    }
}
