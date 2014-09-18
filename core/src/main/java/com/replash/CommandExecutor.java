package com.replash;

public interface CommandExecutor {
    void execute(ReplashRuntime runtime, String commandText) throws ReplashException;
}
