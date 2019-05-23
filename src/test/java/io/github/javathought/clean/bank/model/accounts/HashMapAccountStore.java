package io.github.javathought.clean.bank.model.accounts;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class HashMapAccountStore implements AccountStore {

    private final Map<String, Account> delegate;

    public HashMapAccountStore() {
        this.delegate = new HashMap<>();
    }

    @Override
    public Account put(String accountNumber, Account account) {
        return delegate.put(accountNumber, account);
    }

    @Override
    public Account get(String accountNumber) {
        if (! delegate.containsKey(accountNumber)) {
            throw new NoSuchElementException();
        }
        return delegate.get(accountNumber);
    }

}
