package io.github.javathought.clean.bank.model.accounts.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;
import io.github.javathought.clean.bank.model.operations.Operation;

public class Withdrawal extends Operation {

    public Withdrawal(Amount amountValue) {
        super(amountValue);
    }
}
