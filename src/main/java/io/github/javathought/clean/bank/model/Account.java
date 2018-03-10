package io.github.javathought.clean.bank.model;

import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;
    private final Amount.Currency currency;

    public Account(Amount.Currency currency) {
        this.balance = BigDecimal.ZERO;
        this.currency = currency;
    }

    public Amount balance() {
        return new Amount(balance, currency);
    }

    public void deposit(Amount deposit) throws OperationRefusedException {
        if (deposit.currency() != this.currency) {
            throw new OperationRefusedException(String.format("Account currency is %S", currency));
        }
        balance = balance.add(deposit.value());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("balance", balance)
                .append("currency", currency)
                .toString();
    }
}
