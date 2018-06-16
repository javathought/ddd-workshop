package io.github.javathought.clean.bank.model;

import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.messages.MessageStatus;
import io.github.javathought.clean.bank.model.messages.OperationMessage;
import io.github.javathought.clean.bank.model.messages.RejectReason;
import io.github.javathought.clean.bank.model.operations.Operation;
import io.github.javathought.clean.bank.model.operations.TransactionalOperation;
import io.github.javathought.clean.bank.model.operations.Transfer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferStepdefs implements En {

    public TransferStepdefs(TestWorldState state, HashMapAccountStore accountStore, Bank bank) {
        When("^je transfère (\\d+,\\d+) (.+) du compte '(.+)' vers le compte '(.+)'$",
                (BigDecimal amount, String currency, String originAccount, String destinationAccount) -> {
            try {
                Transfer tx = accountStore.get(originAccount)
                        .transfer(new Amount(amount, Amount.Currency.valueOf(currency)), destinationAccount);
                state.operationAccepted = true;
                state.currentOperation = tx;
                state.currentAccount = originAccount;
            } catch (OperationRefusedException e) {
                state.operationAccepted = false;
            }
        });
        When("^l'opération est au statut en attente$", () ->
            assertThat(state.currentOperation.state()).isEqualTo(Operation.State.PENDING)
        );
        And("^la banque destinatrice renvoie la réponse négative '(.*)'$", (String motif) ->
                bank.receiveResponse(state.currentAccount, (TransactionalOperation) state.currentOperation, MessageStatus.OperationResponse.REJECTED, motif));
        And("^la banque destinatrice renvoie la réponse 'Opération exécutée'$", () ->
                bank.receiveResponse(state.currentAccount, (TransactionalOperation) state.currentOperation, MessageStatus.OperationResponse.PROCESSED, null));
        Then("^l'opération est au statut annulé$", () ->
            assertThat(state.currentOperation.state()).isEqualTo(Operation.State.REVERTED));
        Then("^l'opération est au statut exécuté$", () ->
            assertThat(state.currentOperation.state()).isEqualTo(Operation.State.EXECUTED));
        When("^je reçois de la banque '(.*)' un transfert de (\\d+,\\d+) (.*) pour le compte '(.*)'$",
                (String senderBank, BigDecimal amount, String currency, String destinationAccount) ->
            bank.receiveTransfer(new Amount(amount, Amount.Currency.valueOf(currency)), destinationAccount, senderBank, null));
        Then("^un rejet de l'opération est renvoyé à la banque émettrice '(.*)'$", (String senderBank) -> {
            assertThat(bank.messages())
                    .contains(new OperationMessage(senderBank, MessageStatus.OperationResponse.REJECTED, RejectReason.NO_SUCH_ACCOUNT, null));
        });
    }
}
