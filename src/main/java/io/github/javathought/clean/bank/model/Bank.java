package io.github.javathought.clean.bank.model;

import io.github.javathought.clean.bank.model.operations.TransactionalOperation;
import io.github.javathought.clean.bank.model.operations.Transfer;

import java.util.NoSuchElementException;

public class Bank {

    private final AccountStore store;

    public Bank(AccountStore store) {
        assert store != null;
        this.store = store;
    }

    public void transfer(Transfer transfer) {
        // LATER : with features to communicate between banks
        // should be async
    }

    public void receiveResponse(String accountNumber, TransactionalOperation currentOperation, OperationResponse response, String motif) {
        try {
            Account account = store.get(accountNumber);
            account.processResponse(currentOperation, response, motif);
        } catch (NoSuchElementException e) {
            // LATER : send back error to bank
        }


    }
}
