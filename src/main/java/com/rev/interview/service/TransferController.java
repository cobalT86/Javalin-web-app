package com.rev.interview.service;

import com.google.inject.Inject;
import com.rev.interview.dao.AccountDao;
import com.rev.interview.dao.AccountDaoImpl;
import com.rev.interview.exception.AccountNotFoundException;
import com.rev.interview.exception.AccountsAreTheSameException;
import com.rev.interview.exception.NotEnoughMoneyException;
import com.rev.interview.model.Account;
import io.javalin.Context;
import jdk.net.SocketFlow;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class TransferController {
    /**
     * An example: /transfer/:sum?from=1&to=2
     * NOTE: in case of URL length restrictions, we can pass from & to params in the request body
     */
    public static final String PARAM_FROM = "from";
    public static final String PARAM_TO = "to";
    static final String PARAM_SUM = "sum";
    static final String ID = "id";

    public static final String PATH = "/transfer/" + PARAM_SUM;
    static final String ERR_MSG = "Expected format: /transfer/<BigDecimal>?from=<AccountLongId>&to=<AccountLongId>";

    public static void createAccount(@NotNull Context ctx) {
        AccountDaoImpl accountDao = AccountDaoImpl.instance();
        accountDao.createAccount(ctx.bodyAsClass(Account.class));
        ctx.status(HttpStatus.CREATED_201);
    }

    public static void findAccountById(@NotNull Context ctx)  {
        AccountDaoImpl accountDao = AccountDaoImpl.instance();
        final String id = ctx.pathParam(ID);
        final Account byId = accountDao.findById(Long.parseLong(Objects.requireNonNull(id)));
        ctx.json(byId);
    }

    public static void transferSumBetweenAccounts(@NotNull Context ctx)
            throws AccountNotFoundException, NotEnoughMoneyException, AccountsAreTheSameException {
        AccountDaoImpl accountDao = AccountDaoImpl.instance();
        String sumStr = ctx.pathParam(PARAM_SUM);
        String fromStr = ctx.queryParam(PARAM_FROM);
        String toStr = ctx.queryParam(PARAM_TO);
        BigDecimal sum;
        long fromAccountId;
        long toAccountId;

        try {
            sum = new BigDecimal(sumStr);
            fromAccountId = Long.parseLong(Objects.requireNonNull(fromStr));
            toAccountId = Long.parseLong(Objects.requireNonNull(toStr));
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new IllegalArgumentException(ERR_MSG);
        }

        accountDao.transferMoneyTransactionally(sum, fromAccountId, toAccountId);

        ctx.status(HttpStatus.ACCEPTED_202);
        ctx.result("Sum was transfered to account id : " + toAccountId +
                " which now has a balance of " + AccountDaoImpl.ACCOUNT_STORE.get(toAccountId).getBalance());
    }
}
