package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

import java.time.Instant;
import java.util.Date;

public abstract class Operation {
    private final Date date;
    private final Amount amount;

    Operation(Amount amountValue) {
        this.date = Date.from(Instant.now());
        this.amount = amountValue;
    }

    public Date date() {
        return date;
    }

    public Amount amount() { return amount; }
}
