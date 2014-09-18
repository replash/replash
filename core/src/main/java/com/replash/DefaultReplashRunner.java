package com.replash;

import jline.console.UserInterruptException;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class DefaultReplashRunner implements ReplashRunner {
    private final Set<ReplashEventListener> eventListeners = new HashSet<>();
    private final ConsoleAdapter consoleAdapter;
    private final PromptProvider promptProvider;
    private final CommandExecutor commandExecutor;
    private final ReplashExceptionHandler replashExceptionHandler;

    public DefaultReplashRunner(ConsoleAdapter consoleAdapter, PromptProvider promptProvider, CommandExecutor commandExecutor, ReplashExceptionHandler replashExceptionHandler) {
        this.consoleAdapter = consoleAdapter;
        this.promptProvider = promptProvider;
        this.commandExecutor = commandExecutor;
        this.replashExceptionHandler = replashExceptionHandler;
    }

    @Override
    public void addEventListener(ReplashEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void removeEventListener(ReplashEventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void run(ReplashRuntime runtime) {
        ReplashEventListener eventListenerProxy = createEventListenerProxy();

        eventListenerProxy.beforeRun(runtime);

        try {
            eventListenerProxy.beforeCommand(runtime);

            String commandText;
            while(!runtime.isShutdownRequested() && (commandText = readLine(runtime)) != null) {
                try {
                    eventListenerProxy.beforeExecuteCommand(runtime, commandText);
                    commandExecutor.execute(runtime, commandText);
                    eventListenerProxy.afterExecuteCommand(runtime, commandText);
                }
                catch (ReplashException e) {
                    replashExceptionHandler.handleException(runtime, e);
                }

                eventListenerProxy.afterCommand(runtime);
                eventListenerProxy.beforeCommand(runtime);
            }
        }
        catch(UserInterruptException e) {
            // This isn't an error. This is thrown when the user presses CTRL+C.
        }
        finally {
            eventListenerProxy.beforeShutdown(runtime);
            try {
                consoleAdapter.flush();
            }
            catch (IOException e) {
                throw new ConsoleIOException(e);
            }
            consoleAdapter.shutdown();
            eventListenerProxy.afterShutdown(runtime);
        }

        eventListenerProxy.afterRun(runtime);
    }

    private String readLine(ReplashRuntime runtime) {
        String prompt = promptProvider.getPrompt(runtime);
        try {
            return consoleAdapter.readLine(prompt);
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    private ReplashEventListener createEventListenerProxy() {
        ClassLoader classLoader = getClass().getClassLoader();
        Class<?>[] interfaces = {ReplashEventListener.class};
        DelegatingInvocationHandler invocationHandler = new DelegatingInvocationHandler();
        return (ReplashEventListener)Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    private class DelegatingInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for(ReplashEventListener eventListener : eventListeners) {
                method.invoke(eventListener, args);
            }
            return null;
        }
    }
}
