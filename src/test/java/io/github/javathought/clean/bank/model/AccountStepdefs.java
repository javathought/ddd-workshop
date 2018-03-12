package io.github.javathought.clean.bank.model;

import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class AccountStepdefs implements En {

    private static HashMap<String, Account> accountStore = new HashMap<>();

    private boolean operationAccepted;

    public AccountStepdefs() {
        When("^je crée le compte '(.*)' en (.+)$", (String accountNumber, String currency) ->
                accountStore.put(accountNumber, new Account(Amount.Currency.valueOf(currency))));
        Then("^le solde du compte '(.*)' est (\\d+,\\d+) (.+)$",
                (String accountNumber, Double balance, String currency) ->
                assertThat(accountStore.get(accountNumber).balance())
                        .isEqualTo(new Amount(balance, Amount.Currency.valueOf(currency)))
        );
        When("^je dépose (\\d+,\\d+) (.+) sur le compte '(.+)'$",
                (Double deposit, String currency, String accountNumber) -> {
            try {
                accountStore.get(accountNumber).deposit(new Amount(deposit, Amount.Currency.valueOf(currency)));
                operationAccepted = true;
            } catch (OperationRefusedException e) {
                operationAccepted = false;
            }
        });
        Then("^l'opération est refusée$", () -> assertThat(operationAccepted).isFalse());
    }
}
