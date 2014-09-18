package com.replash;

public class AbstractReplashEventListener implements ReplashEventListener {
    protected AbstractReplashEventListener() {

    }

    @Override
    public void beforeRun(ReplashRuntime runtime) {

    }

    @Override
    public void beforeCommand(ReplashRuntime runtime) {

    }

    @Override
    public void beforeExecuteCommand(ReplashRuntime runtime, String commandText) {

    }

    @Override
    public void afterExecuteCommand(ReplashRuntime runtime, String commandText) {

    }

    @Override
    public void afterCommand(ReplashRuntime runtime) {

    }

    @Override
    public void afterRun(ReplashRuntime runtime) {

    }

    @Override
    public void beforeShutdown(ReplashRuntime runtime) {

    }

    @Override
    public void afterShutdown(ReplashRuntime runtime) {

    }
}
