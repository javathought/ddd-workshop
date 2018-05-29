package io.github.javathought.clean.bank.model;

public interface AccountStore {
    Account put(String accountNumber, Account account);
    Account get(String accountNumber);
}
