package io.github.javathought.clean.bank.model;

import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.operations.Operation;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountStepdefs implements En {

    private Iterator<Operation> histoIterator;
    private List<Operation> histo;

    public AccountStepdefs(TestWorldState state, AccountStore accountStore, Bank bank) {
        When("^je crée le compte '(.*)' en (.+)$", (String accountNumber, String currency) ->
                accountStore.put(accountNumber, new Account(Amount.Currency.valueOf(currency), bank)));
        Then("^le solde du compte '(.*)' est (-?\\d+,\\d+) (.+)$",
                (String accountNumber, BigDecimal balance, String currency) ->
                        assertThat(accountStore.get(accountNumber).balance())
                                .isEqualTo(new Amount(balance, Amount.Currency.valueOf(currency)))
        );

        When("^je dépose (\\d+,\\d+) (.+) sur le compte '(.+)'$",
                (BigDecimal deposit, String currency, String accountNumber) -> {
                    try {
                        state.currentOperation = accountStore.get(accountNumber).deposit(new Amount(deposit, Amount.Currency.valueOf(currency)));
                        state.operationAccepted = true;
                    } catch (OperationRefusedException e) {
                        state.operationAccepted = false;
                    }
                });
        Then("^l'opération est refusée$", () -> assertThat(state.operationAccepted).isFalse());
        Then("^l'opération est acceptée", () -> assertThat(state.operationAccepted).isTrue());
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
        When("^je retire (\\d+,\\d+) (.+) sur le compte '(.+)'$",
                (BigDecimal withdrawal, String currency, String accountNumber) -> {
                    try {
                        state.currentOperation = accountStore.get(accountNumber).withdraw(new Amount(withdrawal, Amount.Currency.valueOf(currency)));
                        state.operationAccepted = true;
                    } catch (OperationRefusedException e) {
                        state.operationAccepted = false;
                    }
                });
        When("^le découvert autorisé du compte '(.+)' est de (\\d+,\\d+)$",
                (String accountNumber, BigDecimal overdraftLimit) ->
                        accountStore.get(accountNumber).changeOverdraftLimit(overdraftLimit));
    }
}
