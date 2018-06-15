package io.github.javathought.clean.bank.model.operations;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationsList {

    private List<Operation> delegate;

    public OperationsList() {
        this.delegate = new CopyOnWriteArrayList<>();
    }

    public void add(Operation operation) {
        delegate.add(0, operation);
    }

    public int size() {
        return delegate.size();
    }

    public Iterator<Operation> iterator() {
        return delegate.iterator();
    }
}
