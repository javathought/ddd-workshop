package io.github.javathought.clean.bank.model;

import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.operations.Deposit;
import io.github.javathought.clean.bank.model.operations.Operation;
import io.github.javathought.clean.bank.model.operations.Withdrawal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Account {
    private BigDecimal balance;
    private final Amount.Currency currency;
    private List<Operation> operations;
    private BigDecimal overdraftLimit;

    public Account(Amount.Currency currency) {
        this.balance = BigDecimal.ZERO;
        this.overdraftLimit = BigDecimal.ZERO;
        this.currency = currency;
        this.operations = new CopyOnWriteArrayList<>();
    }

    public Amount balance() {
        return new Amount(balance, currency);
    }

    public void deposit(Amount deposit) throws OperationRefusedException {
        if (deposit.currency() != this.currency) {
            throw new OperationRefusedException(String.format("Account currency is %s", currency));
        }
        operations.add(0, new Deposit(deposit));
        balance = balance.add(deposit.value());
    }

    public void withdraw(Amount withdrawal) throws OperationRefusedException {
        if (withdrawal.currency() != this.currency) {
            throw new OperationRefusedException(String.format("Account currency is %s", currency));
        }
        if (balance.subtract(withdrawal.value()).compareTo(overdraftLimit.negate()) < 0) {
            throw new OperationRefusedException("Insufficient balance");
        }
        operations.add(0, new Withdrawal(withdrawal));
        balance = balance.subtract(withdrawal.value());
    }

    public List<Operation> operations() {
        return operations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("balance", balance)
                .append("currency", currency)
                .toString();
    }

    public void changeOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }
}
