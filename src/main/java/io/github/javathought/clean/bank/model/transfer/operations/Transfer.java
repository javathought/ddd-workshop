package io.github.javathought.clean.bank.model.transfer.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;
import io.github.javathought.clean.bank.model.operations.Revert;
import io.github.javathought.clean.bank.model.operations.TransactionalOperation;

import java.util.UUID;

import static io.github.javathought.clean.bank.model.operations.OperationType.CREDIT;

public class Transfer extends TransactionalOperation {
    private final String creditedAccount;
    private final UUID transferID;

    public Transfer(Amount amountValue, String destinationAccount) {
        super(amountValue);
        
        this.creditedAccount = destinationAccount;
        transferID = UUID.randomUUID();
    }

    public String creditedAccount() {
        return creditedAccount;
    }

    @Override
    public Revert revert(String motif) {
        return new Revert(this.amount(), CREDIT, this, motif);
    }
}
