package com.replash;

public class ReplashRuntimeException extends RuntimeException {
    public ReplashRuntimeException() {
    }

    public ReplashRuntimeException(String message) {
        super(message);
    }

    public ReplashRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplashRuntimeException(Throwable cause) {
        super(cause);
    }

    public ReplashRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
