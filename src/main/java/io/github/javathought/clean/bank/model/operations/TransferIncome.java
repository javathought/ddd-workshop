package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

public class TransferIncome extends Operation{
    public TransferIncome(Amount amount) {
        super(amount);
    }
}
