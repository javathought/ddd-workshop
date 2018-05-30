package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

import static io.github.javathought.clean.bank.model.operations.Operation.State.REVERTED;

public abstract class TransactionalOperation extends Operation {

    public TransactionalOperation(Amount amountValue) {
        super(amountValue);
    }

    public abstract Revert revert(String motif);

    public void hasBeenReverted() {
        this.state = REVERTED;
    }
}
