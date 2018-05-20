package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public class Withdrawal extends Operation {

    public Withdrawal(Amount amountValue) {
        super(amountValue);
    }
}
