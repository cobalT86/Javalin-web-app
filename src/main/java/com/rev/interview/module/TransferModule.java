package com.rev.interview.module;

import com.google.inject.AbstractModule;
import com.rev.interview.dao.AccountDao;
import com.rev.interview.dao.AccountDaoImpl;
import com.rev.interview.service.TransferController;

public class TransferModule extends AbstractModule {

    @Override
    protected void configure() {
        //bind(AccountDao.class).to(AccountDaoImpl.class);
    }
}
