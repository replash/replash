package com.replash;

import java.io.IOException;

public class ConsoleIOException extends ReplashRuntimeException {
    public ConsoleIOException(IOException e) {
        super(e);
    }
}
