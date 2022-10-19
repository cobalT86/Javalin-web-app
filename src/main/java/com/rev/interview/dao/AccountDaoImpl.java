package com.rev.interview.dao;

import com.rev.interview.exception.AccountNotFoundException;
import com.rev.interview.exception.AccountsAreTheSameException;
import com.rev.interview.exception.NotEnoughMoneyException;
import com.rev.interview.model.Account;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDaoImpl implements AccountDao {

    public static final Map<Long, Account> ACCOUNT_STORE = new ConcurrentHashMap<>();


    private static AccountDaoImpl accountDaoImpl = null;

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl instance() {
        if (accountDaoImpl == null) {
            accountDaoImpl = new AccountDaoImpl();
        }
        return accountDaoImpl;
    }

    @Override
    public Account createAccount(Account newAccount) {
        Account account = Account.builder()
                .id(newAccount.getId())
                .balance(newAccount.getBalance())
                .build();
        ACCOUNT_STORE.put(newAccount.getId(), account);
        return account;
    }

    @Override
    public Account findById(Long id) throws AccountNotFoundException {
        return Optional.ofNullable(ACCOUNT_STORE.get(Objects.requireNonNull(id)))
                .orElseThrow(() -> new AccountNotFoundException("Account : " + id + " doesn't exist!"));
    }

    @Override
    public void transferMoneyTransactionally(BigDecimal sum, Long fromAccountId, Long toAccountId)
            throws AccountNotFoundException, NotEnoughMoneyException, AccountsAreTheSameException {
        if (Objects.equals(fromAccountId, toAccountId)) {
            throw new AccountsAreTheSameException("Accounts : " + fromAccountId + " & " + toAccountId + " are the same!");
        }

        Account from = findById(fromAccountId);
        Account to = findById(toAccountId);

        BigDecimal fromBalanceAfterTransfer = from.getBalance().subtract(sum);
        BigDecimal toBalanceAfterTransfer = to.getBalance().add(sum);

        from.setBalance(fromBalanceAfterTransfer);
        to.setBalance(toBalanceAfterTransfer);
    }
}
