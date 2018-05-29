package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public abstract class TransactionalOperation extends Operation {

    public TransactionalOperation(Amount amountValue) {
        super(amountValue);
    }

    public abstract Revert revert(String motif);
}
