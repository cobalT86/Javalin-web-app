package com.rev.interview.exception;

public class NotEnoughMoneyException extends RuntimeException {

    private static final long serialVersionUID = 4511304443722452636L;

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
