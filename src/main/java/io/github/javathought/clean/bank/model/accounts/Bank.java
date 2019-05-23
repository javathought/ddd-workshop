package io.github.javathought.clean.bank.model.accounts;

import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.messages.MessagesList;
import io.github.javathought.clean.bank.model.messages.MessageStatus;
import io.github.javathought.clean.bank.model.messages.OperationMessage;
import io.github.javathought.clean.bank.model.messages.RejectReason;
import io.github.javathought.clean.bank.model.operations.TransactionalOperation;
import io.github.javathought.clean.bank.model.transfer.operations.Transfer;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.NoSuchElementException;

public class Bank {

    private final AccountStore store;
    private MessagesList messagesList;

    public Bank(AccountStore store) {
        assert store != null;
        this.store = store;
        messagesList = new MessagesList();
    }

    public void transfer(Transfer transfer) {
        // LATER : with features to communicate between banks
        // should be async
    }

    public void receiveTransfer(Amount amount, String destinationAccount, String senderBank, OperationMessage senderMessage) {
        try {
            Account account = store.get(destinationAccount);
            account.receiveTransfer(amount);
        } catch (NoSuchElementException e) {
            sendResponse(senderBank, senderMessage, MessageStatus.OperationResponse.REJECTED, RejectReason.NO_SUCH_ACCOUNT);
            // send back response
        } catch (OperationRefusedException e) {
            // send back response Conversion ??
        }
    }

    public void receiveResponse(String accountNumber, TransactionalOperation currentOperation, MessageStatus.OperationResponse response, String motif) {
        try {
            Account account = store.get(accountNumber);
            account.processResponse(currentOperation, response, motif);
        } catch (NoSuchElementException e) {
            // LATER : send back error to bank
        }
    }

    public MessagesList messages() {
        return messagesList;
    }

    private void sendResponse(String senderBank, OperationMessage senderMessage, MessageStatus.OperationResponse response, RejectReason reason) {
        messagesList.add(new OperationMessage(senderBank, response, reason, senderMessage));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("store", store)
                .append("messagesList", messagesList)
                .toString();
    }
}
