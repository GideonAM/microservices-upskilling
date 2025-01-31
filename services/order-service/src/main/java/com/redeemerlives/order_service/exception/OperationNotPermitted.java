package com.redeemerlives.order_service.exception;

public class OperationNotPermitted extends RuntimeException {
    public OperationNotPermitted(String message) {
        super(message);
    }
}
