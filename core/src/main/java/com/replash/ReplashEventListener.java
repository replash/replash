package com.replash;

public interface ReplashEventListener {
    void beforeRun(ReplashRuntime runtime);
    void beforeCommand(ReplashRuntime runtime);
    void beforeExecuteCommand(ReplashRuntime runtime, String commandText);
    void afterExecuteCommand(ReplashRuntime runtime, String commandText);
    void afterCommand(ReplashRuntime runtime);
    void afterRun(ReplashRuntime runtime);
    void beforeShutdown(ReplashRuntime runtime);
    void afterShutdown(ReplashRuntime runtime);
}
