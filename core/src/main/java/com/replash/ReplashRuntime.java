package com.replash;

import com.replash.commands.CommandTree;

public class ReplashRuntime {
    private final ConsoleAdapter consoleAdapter;
    private final CommandTextParser commandTextParser;
    private final CommandResolver commandResolver;
    private final CommandTree commandTree;
    private final PromptProvider promptProvider;
    private final ReplashRunner replashRunner;
    private final HelpCommandHandler helpCommandHandler;
    private boolean shutdownRequested;

    public ReplashRuntime(ConsoleAdapter consoleAdapter, CommandTextParser commandTextParser, CommandResolver commandResolver, CommandTree commandTree, PromptProvider promptProvider, ReplashRunner replashRunner, HelpCommandHandler helpCommandHandler) {
        this.consoleAdapter = consoleAdapter;
        this.commandTextParser = commandTextParser;
        this.commandResolver = commandResolver;
        this.commandTree = commandTree;
        this.promptProvider = promptProvider;
        this.replashRunner = replashRunner;
        this.helpCommandHandler = helpCommandHandler;
    }

    public ConsoleAdapter getConsoleAdapter() {
        return consoleAdapter;
    }

    public CommandTextParser getCommandTextParser() {
        return commandTextParser;
    }

    public CommandResolver getCommandResolver() {
        return commandResolver;
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

    public HelpCommandHandler getHelpCommandHandler() {
        return helpCommandHandler;
    }

    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    public void shutdownRequested() {
        shutdownRequested = true;
    }
}
