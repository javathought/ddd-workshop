package io.github.javathought.clean.bank.model;

import cucumber.api.java8.En;
import io.github.javathought.clean.bank.model.operations.Operation;
import io.github.javathought.clean.bank.model.operations.Transfer;

public class GenericState {
    public Operation currentOperation;
    public boolean operationAccepted;


}
