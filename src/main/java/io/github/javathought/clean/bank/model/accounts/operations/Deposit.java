package io.github.javathought.clean.bank.model.accounts.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;
import io.github.javathought.clean.bank.model.operations.Operation;

public class Deposit extends Operation {
    public Deposit(Amount amountValue) {
        super(amountValue);
    }
}
