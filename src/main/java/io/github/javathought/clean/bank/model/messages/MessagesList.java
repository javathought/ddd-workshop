package io.github.javathought.clean.bank.model.messages;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class MessagesList implements Iterable<OperationMessage> {

    private List<OperationMessage> delegate;

    public MessagesList() {
        this.delegate = new CopyOnWriteArrayList<>();
    }

    public void add(OperationMessage operation) {
        delegate.add(operation);
    }

    @Override
    public Iterator<OperationMessage> iterator() {
        return delegate.iterator();
    }

    @Override
    public void forEach(Consumer<? super OperationMessage> action) {
        delegate.forEach(action);
    }

    @Override
    public Spliterator<OperationMessage> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("delegate", delegate)
                .toString();
    }
}
