package io.github.javathought.clean.bank.model.transfer.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;
import io.github.javathought.clean.bank.model.operations.Operation;

public class TransferIncome extends Operation {
    public TransferIncome(Amount amount) {
        super(amount);
    }
}
