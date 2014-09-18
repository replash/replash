package com.replash.spring;

import com.replash.AbstractReplashEventListener;
import com.replash.Replash;
import com.replash.ReplashRuntime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cbeattie on 07/09/14.
 */
public class SpringReplashRunnerBean implements SmartLifecycle, ApplicationContextAware {
    @Autowired(required = false)
    private ExecutorService executorService;

    @Autowired(required = false)
    private SpringReplashBuilder replashBuilder;

    private ApplicationContext applicationContext;
    private Replash replash;
    private volatile boolean running;
    private boolean shutdownExecutor;

    public SpringReplashRunnerBean() {
        this(null);
    }

    public SpringReplashRunnerBean(SpringReplashBuilder replashBuilder) {
        this.replashBuilder = replashBuilder;
    }

    @Override
    public void start() {
        if(replashBuilder == null) {
            replashBuilder = new SpringReplashBuilder();
            if(applicationContext != null) {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(replashBuilder);
            }
        }

        if(executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
            shutdownExecutor = true;

            replashBuilder.withEventListener(new AbstractReplashEventListener(){
                @Override
                public void afterShutdown(ReplashRuntime runtime) {
                    executorService.shutdown();
                }
            });
        }

        replash = replashBuilder.build();

        replash.runAsync(executorService);
        running = true;
    }

    @Override
    public void stop() {
        running = false;
        if(shutdownExecutor) {
            executorService.shutdownNow();
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

}
