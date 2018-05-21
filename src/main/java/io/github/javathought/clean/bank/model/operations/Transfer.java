package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public class Transfer extends Operation {
    public Transfer(Amount amountValue) {
        super(amountValue);
    }
}
