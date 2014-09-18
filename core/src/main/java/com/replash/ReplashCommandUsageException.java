package com.replash;

public class ReplashCommandUsageException extends ReplashRuntimeException {
    public ReplashCommandUsageException() {
    }

    public ReplashCommandUsageException(String message) {
        super(message);
    }

    public ReplashCommandUsageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplashCommandUsageException(Throwable cause) {
        super(cause);
    }

    public ReplashCommandUsageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
