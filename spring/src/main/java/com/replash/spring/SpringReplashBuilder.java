package com.replash.spring;

import com.replash.*;
import jline.console.ConsoleReader;
import com.replash.help.DetailedHelpHandler;
import com.replash.help.SimpleHelpHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringReplashBuilder extends ReplashBuilder {
    private CommandExecutor springCommandExecutor;
    private ReplashRunner springReplashRunner;

    public ConsoleReader getConsoleReader() {
        return jlineConsoleReader;
    }

    @Autowired(required = false)
    public void setConsoleReader(ConsoleReader consoleReader) {
        this.jlineConsoleReader = consoleReader;
    }

    public ConsoleAdapter getConsoleAdapter() {
        return consoleAdapter;
    }

    @Autowired(required = false)
    public void setConsoleAdapter(ConsoleAdapter consoleAdapter) {
        this.consoleAdapter = consoleAdapter;
    }

    public PromptProvider getPromptProvider() {
        return promptProvider;
    }

    @Autowired(required = false)
    public void setPromptProvider(PromptProvider promptProvider) {
        this.promptProvider = promptProvider;
    }

    public CommandTextParser getCommandTextParser() {
        return commandTextParser;
    }

    @Autowired(required = false)
    public void setCommandTextParser(CommandTextParser commandTextParser) {
        this.commandTextParser = commandTextParser;
    }

    public CommandResolver getCommandResolver() {
        return commandResolver;
    }

    @Autowired(required = false)
    public void setCommandResolver(CommandResolver commandResolver) {
        this.commandResolver = commandResolver;
    }

    public SimpleHelpHandler getSimpleHelpHandler() {
        return simpleHelpHandler;
    }

    @Autowired(required = false)
    public void setSimpleHelpHandler(SimpleHelpHandler simpleHelpHandler) {
        this.simpleHelpHandler = simpleHelpHandler;
    }

    public DetailedHelpHandler getDetailedHelpHandler() {
        return detailedHelpHandler;
    }

    @Autowired(required = false)
    public void setDetailedHelpHandler(DetailedHelpHandler detailedHelpHandler) {
        this.detailedHelpHandler = detailedHelpHandler;
    }

    public ReflectionCommandFactory getReflectionCommandFactory() {
        return reflectionCommandFactory;
    }

    @Autowired(required = false)
    public void setReflectionCommandFactory(ReflectionCommandFactory reflectionCommandFactory) {
        this.reflectionCommandFactory = reflectionCommandFactory;
    }

    public ReplashExceptionHandler getReplashExceptionHandler() {
        return replashExceptionHandler;
    }

    @Autowired(required = false)
    public void setReplashExceptionHandler(ReplashExceptionHandler replashExceptionHandler) {
        this.replashExceptionHandler = replashExceptionHandler;
    }

    public CommandExecutor getCommandExecutor() {
        return springCommandExecutor;
    }

    @Autowired(required = false)
    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.springCommandExecutor = commandExecutor;
    }

    public ReplashRunner getReplashRunner() {
        return springReplashRunner;
    }

    @Autowired(required = false)
    public void setReplashRunner(ReplashRunner replashRunner) {
        this.springReplashRunner = replashRunner;
    }

    @Override
    protected CommandExecutor createCommandExecutor(CommandTextParser commandTextParser, CommandResolver commandResolver) {
        if(springCommandExecutor != null) {
            return springCommandExecutor;
        }

        return super.createCommandExecutor(commandTextParser, commandResolver);
    }

    @Override
    protected ReplashRunner createRunner(ConsoleAdapter consoleAdapter, PromptProvider promptProvider, CommandExecutor commandExecutor, ReplashExceptionHandler replashExceptionHandler) {
        if(springReplashRunner != null) {
            return springReplashRunner;
        }

        return super.createRunner(consoleAdapter, promptProvider, commandExecutor, replashExceptionHandler);
    }
}
