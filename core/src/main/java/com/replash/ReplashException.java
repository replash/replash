package com.replash;

public class ReplashException extends Exception {
    public ReplashException() {
    }

    public ReplashException(String message) {
        super(message);
    }

    public ReplashException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplashException(Throwable cause) {
        super(cause);
    }

    public ReplashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
