package io.github.javathought.clean.bank.model;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountStepdefs implements En {

    private static HashMap<String, Account> accountStore = new HashMap<>();

    private boolean operationAccepted;

    public AccountStepdefs() {
        When("^je crée le compte '(.*)' en (.+)$", (String accountNumber, String currency) ->
                accountStore.put(accountNumber, new Account(Amount.Currency.valueOf(currency))));
        Then("^le solde du compte '(.*)' est (\\d+,\\d+) (.+)$",
                (String accountNumber, BigDecimal balance, String currency) ->
                assertThat(accountStore.get(accountNumber).balance())
                        .isEqualTo(new Amount(balance, Amount.Currency.valueOf(currency)))
        );
        When("^je dépose (\\d+,\\d+) (.+) sur le compte '(.+)'$",
                (BigDecimal deposit, String currency, String accountNumber) -> {
            try {
                accountStore.get(accountNumber).deposit(new Amount(deposit, Amount.Currency.valueOf(currency)));
                operationAccepted = true;
            } catch (OperationRefusedException e) {
                operationAccepted = false;
            }
        });
        Then("^l'opération est refusée$", () -> assertThat(operationAccepted).isFalse());
        When("^je consulte le compte 'A(\\d+)'$", (Integer arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^la taille de l'historique est (\\d+)$", (Integer arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^la dernière opération est de (\\d+) EUR$", (Integer arg0) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^l'opération précédente est de (\\d+),(\\d+) EUR$", (Integer arg0, Integer arg1) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
