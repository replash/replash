package com.replash;

/**
 * Created by cbeattie on 24/08/14.
 */
public class AmbiguousCommandExecutorPluginException extends ReplashRuntimeException {
    public AmbiguousCommandExecutorPluginException() {
        super("Ambiguous CommandExecutorFactory instances are available");
    }
}
