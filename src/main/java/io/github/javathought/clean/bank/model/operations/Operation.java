package io.github.javathought.clean.bank.model.operations;

import io.github.javathought.clean.bank.model.accounts.Amount;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static io.github.javathought.clean.bank.model.operations.Operation.State.EXECUTED;
import static io.github.javathought.clean.bank.model.operations.Operation.State.PENDING;

public abstract class Operation {
    private final UUID id;
    private final Date date;
    private final Amount amount;
    protected State state;

    protected Operation(Amount amountValue) {
        assert amountValue != null;
        id = UUID.randomUUID();
        this.date = Date.from(Instant.now());
        this.amount = amountValue;
        this.state = PENDING;
    }

    public UUID id() {
        return id;
    }

    public Date date() {
        return date;
    }

    public Amount amount() { return amount; }

    public State state() {
        return state;
    }

    public void hasBeenExecuted() {
        this.state = EXECUTED;
    }

    public enum State {
        PENDING,
        EXECUTED,
        REVERTED
    }
}
