package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.Amount;

import java.time.Instant;
import java.util.Date;

import static io.github.javathought.clean.bank.model.operations.Operation.State.PENDING;

public abstract class Operation {
    private final Date date;
    private final Amount amount;
    private final State state;

    Operation(Amount amountValue) {
        this.date = Date.from(Instant.now());
        this.amount = amountValue;
        this.state = PENDING;
    }

    public Date date() {
        return date;
    }

    public Amount amount() { return amount; }

    public State state() {
        return state;
    }

    public enum State {
        PENDING,
        EXECUTED
    }
}
