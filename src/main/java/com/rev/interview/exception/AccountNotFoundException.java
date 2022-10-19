package com.rev.interview.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5723517810557339595L;

    public AccountNotFoundException(String message) {
        super(message);
    }
}
