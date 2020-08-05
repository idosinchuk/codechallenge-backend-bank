package com.idosinchuk.codechallenge.backend.bank.application.use_case;

import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionStatusDTO;
import com.idosinchuk.codechallenge.backend.bank.application.dto.enums.ChannelType;
import com.idosinchuk.codechallenge.backend.bank.application.service.TransactionService;
import com.idosinchuk.codechallenge.backend.bank.domain.exception.InvalidChannelProvidedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetTransactionStatusUseCase {

    private final TransactionService transactionService;

    public TransactionStatusDTO getTransactionStatus(String reference, String channel) throws ParseException {

        boolean validChannel = isValidChannel(channel);

        if (!validChannel) {
            log.error("Channel provided is not valid: {}", channel);
            throw new InvalidChannelProvidedException(channel);
        }

        return transactionService.getTransactionStatus(reference, channel);
    }

    private static boolean isValidChannel(String channel) {
        return channel.equals(ChannelType.CLIENT.toString()) || channel.equals(ChannelType.ATM.toString()) || channel.equals(ChannelType.INTERNAL.toString());
    }

}
