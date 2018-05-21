package io.github.javathought.clean.bank.model;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;

import java.math.BigDecimal;

public class WithdrawStepdefs implements En {
    private final GenericState state;
    private final AccountStore accountStore;

    public WithdrawStepdefs(GenericState state, AccountStore accountStore) {
        this.state = state;
        this.accountStore = accountStore;
        When("^je transfère (\\d+,\\d+) (.+) du compte '(.+)' vers le compte '(.+)'$",
                (BigDecimal amount, String currency, String originAccount, String destinationAccount) -> {
            try {
                accountStore.get(originAccount)
                        .transfer(new Amount(amount, Amount.Currency.valueOf(currency)), destinationAccount);
                state.operationAccepted = true;
            } catch (OperationRefusedException e) {
                state.operationAccepted = false;
            }
        });
        When("^l'opération est en attente$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^l'opération est annulée$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^je transfère (\\d+),(\\d+) EUR du compte 'T(\\d+)' vers le compte 'I(\\d+)'$", (Integer arg0, Integer arg1, Integer arg2, Integer arg3) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^la banque destinatrice renvoie la réponse négative 'Compte inexistant'$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^la banque destinatrice renvoie la réponse 'Opération exécutée'$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
