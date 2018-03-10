package io.github.javathought.clean.bank.model.exceptions;

public class OperationRefusedException extends Exception {
    public OperationRefusedException(String msg) {
        super(msg);
    }
}
