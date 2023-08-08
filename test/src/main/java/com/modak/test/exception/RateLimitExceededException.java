package com.modak.test.exception;

public class RateLimitExceededException  extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
