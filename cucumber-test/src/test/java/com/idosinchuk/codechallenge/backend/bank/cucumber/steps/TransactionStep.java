package com.idosinchuk.codechallenge.backend.bank.cucumber.steps;

import com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.AccountPresentation;
import io.cucumber.java.en.Given;
import org.springframework.web.client.RestTemplate;

public class TransactionStep {

    protected final String DEFAULT_URL = "http://localhost:8080/api/account";
    protected RestTemplate restTemplate = new RestTemplate();

    @Given("A userId {string} and clientId {string} with orange account")
    public void aTransactionThatIsNotStoredinOurSystemAndWithExistingAccount() {

        AccountPresentation accountPresentation = com.idosinchuk.codechallenge.backend.bank.infrastructure.presentation.AccountPresentation.builder().accountIban("ES0401821239005846771465").balance(500.00).build();

        String hola = restTemplate.postForObject(DEFAULT_URL, accountPresentation, String.class);

        String goa = "1";

    }


}
