package com.replash;

import java.util.concurrent.ExecutorService;

public class DefaultReplash implements Replash {
    private final ReplashRuntime replashRuntime;
    private final ReplashRunner replashRunner;
    private final ReplashRunnable replashRunnable;

    public DefaultReplash(ReplashRuntime replashRuntime, ReplashRunner replashRunner) {
        this.replashRuntime = replashRuntime;
        this.replashRunner = replashRunner;
        this.replashRunnable = new ReplashRunnable();
    }

    @Override
    public void run() {
        replashRunnable.run();
    }

    @Override
    public void runAsync(ExecutorService executorService) {
        executorService.execute(replashRunnable);
    }

    private class ReplashRunnable implements Runnable {
        @Override
        public void run() {
            replashRunner.run(replashRuntime);
        }
    }
}
