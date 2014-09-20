package com.replash;

public class AmbiguousCommandExecutorPluginException extends ReplashRuntimeException {
    public AmbiguousCommandExecutorPluginException() {
        super("Ambiguous CommandExecutorFactory instances are available");
    }
}
