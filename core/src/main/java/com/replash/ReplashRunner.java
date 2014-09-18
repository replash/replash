package com.replash;

public interface ReplashRunner {
    void addEventListener(ReplashEventListener eventListener);
    void removeEventListener(ReplashEventListener eventListener);
    void run(ReplashRuntime runtime);
}
