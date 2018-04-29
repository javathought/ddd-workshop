package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public class Deposit extends Operation {
    public Deposit(Amount amountValue) {
        super(amountValue);
    }
}
