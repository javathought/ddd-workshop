package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;

public class Revert extends Operation {

    private final Operation originalOperation;
    private final OperationType type;
    private final String motif;

    public Revert(Amount amount, OperationType type, Operation originalOperation, String motif) {
        super(amount);
        this.type = type;
        this.originalOperation = originalOperation;
        this.motif = motif;
    }

    public Operation originalOperation() {
        return originalOperation;
    }

    public OperationType type() {
        return type;
    }

    public String motif() {
        return motif;
    }
}
