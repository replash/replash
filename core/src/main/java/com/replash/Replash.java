package com.replash;

import java.util.concurrent.ExecutorService;

public interface Replash {
    void run();
    void runAsync(ExecutorService executorService);
}
