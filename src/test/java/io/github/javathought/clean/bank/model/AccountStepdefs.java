package io.github.javathought.clean.bank.model;

import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.operations.Operation;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountStepdefs implements En {

    private static HashMap<String, Account> accountStore = new HashMap<>();
    private Iterator<Operation> histoIterator;
    private List<Operation> histo;
    private boolean operationAccepted;
    private Account account;

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
        Then("^l'opération est acceptée", () -> assertThat(operationAccepted).isTrue());
        When("^je consulte le compte '(.+)'$", (String accountNum) ->
            histo = accountStore.get(accountNum).operations());
        Then("^la taille de l'historique est (\\d+)$", (Integer arg0) -> {
            assertThat(histo.size()).isEqualTo(arg0);
            histoIterator = histo.iterator();
        });
        And("^la dernière opération est de (\\d+,\\d+) (.+)$", (BigDecimal value, String currency) ->
                assertThat(histoIterator.next())
                .extracting("amount.value", "amount.currency")
                .contains(value, Amount.Currency.valueOf(currency))
        );
        And("^l'opération précédente est de (\\d+,\\d+) (.+)$", (BigDecimal value, String currency) ->
            assertThat(histoIterator.next())
                    .hasFieldOrPropertyWithValue("amount.value", value)
                    .hasFieldOrPropertyWithValue("amount.currency", Amount.Currency.valueOf(currency))
        );
    }
}
