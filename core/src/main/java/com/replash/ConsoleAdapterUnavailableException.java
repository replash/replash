package com.replash;

public class ConsoleAdapterUnavailableException extends ReplashRuntimeException {
    public ConsoleAdapterUnavailableException() {
        super("Console adapter is unavailable in this execution context");
    }
}
