package com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.idosinchuk.codechallenge.backend.bank.application.dto.TransactionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountPresentation {

    @ApiModelProperty(required = true, value = "The IBAN number of the account where the transaction has happened.", example = "ES6004871262385886192518", position = 1)
    @NotBlank(message = "Account iban is mandatory.")
    private String accountIban;

    @ApiModelProperty(required = false, value = "Balance of the account.", example = "2000.00", position = 2)
    private Double balance;
}
