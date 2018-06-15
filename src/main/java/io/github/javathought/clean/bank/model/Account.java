package io.github.javathought.clean.bank.model;

import io.github.javathought.clean.bank.model.exceptions.OperationRefusedException;
import io.github.javathought.clean.bank.model.messages.MessageStatus;
import io.github.javathought.clean.bank.model.operations.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Account {
    private final Bank bank;
    private BigDecimal balance;
    private final Amount.Currency currency;
    private OperationsList operations;
    private Map<UUID, TransactionalOperation> pendingOperations;
    private BigDecimal overdraftLimit;

    public Account(Amount.Currency currency, Bank bank) {
        assert currency != null;
        assert bank != null;
        this.bank = bank;
        this.balance = BigDecimal.ZERO;
        this.overdraftLimit = BigDecimal.ZERO;
        this.currency = currency;
        this.operations = new OperationsList();
        this.pendingOperations = new ConcurrentHashMap<>();
    }

    public Amount balance() {
        return new Amount(balance, currency);
    }

    public Deposit deposit(Amount deposit) throws OperationRefusedException {
        checkCurrency(deposit);
        Deposit operation = new Deposit(deposit);
        operations.add(operation);
        balance = balance.add(deposit.value());
        operation.hasBeenExecuted();
        return operation;
    }

    public Withdrawal withdraw(Amount withdrawal) throws OperationRefusedException {
        checkCurrency(withdrawal);
        checkOverdraft(withdrawal);
        Withdrawal operation = new Withdrawal(withdrawal);
        operations.add(operation);
        balance = balance.subtract(withdrawal.value());
        operation.hasBeenExecuted();
        return operation;
    }

    public OperationsList operations() {
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

    public TransferIncome receiveTransfer(Amount amount) throws OperationRefusedException {
        checkCurrency(amount);
        TransferIncome transferIncome = new TransferIncome(amount);
        balance = balance.add(amount.value());
        operations.add(transferIncome);
        transferIncome.hasBeenExecuted();
        return transferIncome;
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

    public void processResponse(TransactionalOperation currentOperation, MessageStatus.OperationResponse response, String motif) {
        TransactionalOperation pending = pendingOperations.get(currentOperation.id());
        if (noSuchPendingOperation(currentOperation, pending)) {
            // TODO : bank should return reponse

        } else {
            applyResponse(currentOperation, response, motif);
        }

    }

    private void applyResponse(TransactionalOperation currentOperation, MessageStatus.OperationResponse response, String motif) {
        removeOperationFromPending(currentOperation);
        if (response.equals(MessageStatus.OperationResponse.PROCESSED)) {
            currentOperation.hasBeenExecuted();
        } else {
            revertOperation(currentOperation, motif);
        }
    }

    private void revertOperation(TransactionalOperation currentOperation, String motif) {
        Revert revert = currentOperation.revert(motif);
        operations.add(revert);
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
        operations.add(currentOperation);
    }

    private boolean noSuchPendingOperation(TransactionalOperation currentOperation, TransactionalOperation pending) {
        return pending == null || !pending.equals(currentOperation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("balance", balance)
                .append("currency", currency)
                .toString();
    }

}
