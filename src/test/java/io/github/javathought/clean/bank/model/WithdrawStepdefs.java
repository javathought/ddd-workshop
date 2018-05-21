package io.github.javathought.clean.bank.model;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import cucumber.api.java8.Tr;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.operations.Operation;
import io.github.javathought.clean.bank.model.operations.Transfer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class WithdrawStepdefs implements En {
    private final GenericState state;
    private final AccountStore accountStore;

    public WithdrawStepdefs(GenericState state, AccountStore accountStore) {
        this.state = state;
        this.accountStore = accountStore;
        When("^je transfère (\\d+,\\d+) (.+) du compte '(.+)' vers le compte '(.+)'$",
                (BigDecimal amount, String currency, String originAccount, String destinationAccount) -> {
            try {
                Transfer tx = accountStore.get(originAccount)
                        .transfer(new Amount(amount, Amount.Currency.valueOf(currency)), destinationAccount);
                state.operationAccepted = true;
                state.currentOperation = tx;
            } catch (OperationRefusedException e) {
                state.operationAccepted = false;
            }
        });
        When("^l'opération est en attente$", () ->
            assertThat(state.currentOperation.state()).isEqualTo(Operation.State.PENDING)
        );
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
