package com.rev.interview.dao;

import com.rev.interview.exception.AccountNotFoundException;
import com.rev.interview.exception.AccountsAreTheSameException;
import com.rev.interview.exception.NotEnoughMoneyException;
import com.rev.interview.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account createAccount(Account account);

    Account findById(Long id) throws AccountNotFoundException;

    void transferMoneyTransactionally(BigDecimal sum, Long fromAccountId, Long toAccountId)
            throws AccountNotFoundException, NotEnoughMoneyException, AccountsAreTheSameException;
}
