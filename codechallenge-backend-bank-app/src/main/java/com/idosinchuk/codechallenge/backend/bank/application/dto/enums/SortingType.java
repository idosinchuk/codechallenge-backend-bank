package com.idosinchuk.codechallenge.backend.bank.application.dto.enums;

import lombok.Getter;

@Getter
public enum SortingType {

    ASCENDING,
    DESCENDING;

    public static SortingType getSortingType(String sorting) {
        return SortingType.valueOf(sorting.toUpperCase());
    }
}
