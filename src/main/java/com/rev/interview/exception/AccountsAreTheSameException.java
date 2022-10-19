package com.rev.interview.exception;

public class AccountsAreTheSameException extends RuntimeException {

    private static final long serialVersionUID = 790378258673651394L;

    public AccountsAreTheSameException(String message) {
        super(message);
    }
}
