package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public class Transfer extends Operation {
    private final String creditedAccount;

    public Transfer(Amount amountValue, String destinationAccount) {
        super(amountValue);
        this.creditedAccount = destinationAccount;
    }

    public String creditedAccount() {
        return creditedAccount;
    }
}
