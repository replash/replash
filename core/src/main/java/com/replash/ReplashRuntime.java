package com.replash;

import com.replash.commands.CommandTree;

public class ReplashRuntime {
    private final ConsoleAdapter consoleAdapter;
    private final CommandTree commandTree;
    private final PromptProvider promptProvider;
    private final ReplashRunner replashRunner;
    private boolean shutdownRequested;

    public ReplashRuntime(ConsoleAdapter consoleAdapter, CommandTree commandTree, PromptProvider promptProvider, ReplashRunner replashRunner) {
        this.consoleAdapter = consoleAdapter;
        this.commandTree = commandTree;
        this.promptProvider = promptProvider;
        this.replashRunner = replashRunner;
    }

    public ConsoleAdapter getConsoleAdapter() {
        return consoleAdapter;
    }

    public CommandTree getCommandTree() {
        return commandTree;
    }

    public PromptProvider getPromptProvider() {
        return promptProvider;
    }

    public ReplashRunner getReplashRunner() {
        return replashRunner;
    }

    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    public void shutdownRequested() {
        shutdownRequested = true;
    }
}
