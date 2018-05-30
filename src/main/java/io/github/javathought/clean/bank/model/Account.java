package io.github.javathought.clean.bank.model;

import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.operations.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Account {
    private final Bank bank;
    private BigDecimal balance;
    private final Amount.Currency currency;
    private List<Operation> operations;
    private Map<UUID, TransactionalOperation> pendingOperations;
    private BigDecimal overdraftLimit;

    public Account(Amount.Currency currency, Bank bank) {
        assert currency != null;
        assert bank != null;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        this.overdraftLimit = BigDecimal.ZERO;
        this.currency = currency;
        this.operations = new CopyOnWriteArrayList<>();
        this.pendingOperations = new ConcurrentHashMap<>();
    }

    public Amount balance() {
        return new Amount(balance, currency);
    }

    public Deposit deposit(Amount deposit) throws OperationRefusedException {
        checkCurrency(deposit);
        Deposit operation = new Deposit(deposit);
        addOperation(operation);
        balance = balance.add(deposit.value());
        operation.hasBeenExecuted();
        return operation;
    }

    public Withdrawal withdraw(Amount withdrawal) throws OperationRefusedException {
        checkCurrency(withdrawal);
        checkOverdraft(withdrawal);
        Withdrawal operation = new Withdrawal(withdrawal);
        addOperation(operation);
        balance = balance.subtract(withdrawal.value());
        operation.hasBeenExecuted();
        return operation;
    }

    public List<Operation> operations() {
        return operations;
    }

    public void changeOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public Transfer transfer(Amount amount, String destinationAccount) throws OperationRefusedException {
        checkCurrency(amount);
        checkOverdraft(amount);
        Transfer transfer = new Transfer(amount, destinationAccount);
        pendingOperations.put(transfer.id(), transfer);
        balance = balance.subtract(amount.value());

        bank.transfer(transfer);
        return transfer;
    }

    private void checkCurrency(Amount deposit) throws OperationRefusedException {
        if (deposit.currency() != this.currency) {
            throw new OperationRefusedException(String.format("Account currency is %s", currency));
        }
    }

    private void checkOverdraft(Amount withdrawal) throws OperationRefusedException {
        if (balance.subtract(withdrawal.value()).compareTo(overdraftLimit.negate()) < 0) {
            throw new OperationRefusedException("Insufficient balance");
        }
    }

    public void processResponse(TransactionalOperation currentOperation, OperationResponse response, String motif) {
        TransactionalOperation pending = pendingOperations.get(currentOperation.id());
        if (noSuchPendingOperation(currentOperation, pending)) {
            // TODO : bank should return reponse
            ;
        } else {
            applyResponse(currentOperation, response, motif);
        }

    }

    private void applyResponse(TransactionalOperation currentOperation, OperationResponse response, String motif) {
        removeOperationFromPending(currentOperation);
        if (response.equals(OperationResponse.PROCESSED)) {
            currentOperation.hasBeenExecuted();
        } else {
            revertOperation(currentOperation, motif);
        }
    }

    private void revertOperation(TransactionalOperation currentOperation, String motif) {
        Revert revert = currentOperation.revert(motif);
        addOperation(revert);
        OperationType type = revert.type();
        if (type == OperationType.CREDIT) {
            balance = balance.add(revert.amount().value());
        } else if (type == OperationType.DEBIT) {
            balance = balance.subtract(revert.amount().value());
        }
        currentOperation.hasBeenReverted();
        revert.hasBeenExecuted();
    }

    private void removeOperationFromPending(TransactionalOperation currentOperation) {
        pendingOperations.remove(currentOperation.id());
        currentOperation.hasBeenExecuted();
        addOperation(currentOperation);
    }

    private boolean noSuchPendingOperation(TransactionalOperation currentOperation, TransactionalOperation pending) {
        return pending == null || !pending.equals(currentOperation);
    }

    private void addOperation(Operation operation) {
        operations.add(0, operation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("balance", balance)
                .append("currency", currency)
                .toString();
    }

}
