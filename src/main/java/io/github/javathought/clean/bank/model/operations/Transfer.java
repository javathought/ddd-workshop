package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

import static io.github.javathought.clean.bank.model.operations.OperationType.CREDIT;

public class Transfer extends TransactionalOperation {
    private final String creditedAccount;

    public Transfer(Amount amountValue, String destinationAccount) {
        super(amountValue);
        this.creditedAccount = destinationAccount;
    }

    public String creditedAccount() {
        return creditedAccount;
    }

    @Override
    public Revert revert(String motif) {
        return new Revert(this.amount(), CREDIT, this, motif);
    }
}
