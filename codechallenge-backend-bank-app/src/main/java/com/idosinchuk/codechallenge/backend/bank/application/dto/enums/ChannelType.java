package com.idosinchuk.codechallenge.backend.bank.application.dto.enums;

import lombok.Getter;

@Getter
public enum ChannelType {

    CLIENT,
    ATM,
    INTERNAL;

    public static ChannelType getChannelType(String channel){
        return ChannelType.valueOf(channel.toUpperCase());
    }
}
