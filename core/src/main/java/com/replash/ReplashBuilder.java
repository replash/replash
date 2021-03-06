package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;
import com.replash.commands.builtin.ExitCommand;
import com.replash.commands.builtin.HelpCommand;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplashBuilder {
    protected CommandTree commandTree = new CommandTree();
    protected BannerEventListener bannerEventListener = new BannerEventListener();
    protected boolean ignoreBuiltinCommands = false;
    protected ConsoleReader jlineConsoleReader;
    protected ConsoleAdapter consoleAdapter;
    protected PromptProvider promptProvider = new StaticTextPromptProvider();
    protected CommandTextParser commandTextParser = new DefaultCommandTextParser();
    protected CommandResolver commandResolver = new DefaultCommandResolver();
    protected HelpCommandHandler helpCommandHandler = new DefaultHelpCommandHandler();
    protected ReflectionCommandFactory reflectionCommandFactory = new DefaultReflectionCommandFactory();
    protected ReplashExceptionHandler replashExceptionHandler = new DefaultReplashExceptionHandler();
    protected List<ReplashEventListener> replashEventListeners = new ArrayList<>();
    protected CommandExecutor commandExecutor;
    protected ReplashRunner runner;

    public Replash build() {
        if(jlineConsoleReader == null) {
            try {
                jlineConsoleReader = new ConsoleReader();
            }
            catch (IOException e) {
                throw new ConsoleIOException(e);
            }
        }

        configureJLineConsoleReader(jlineConsoleReader);

        if(consoleAdapter == null) {
            consoleAdapter = createConsole(jlineConsoleReader);
        }

        applyTabCompletionHandler(consoleAdapter);

        if(commandExecutor == null) {
            commandExecutor = createCommandExecutor(commandTextParser, commandResolver);
        }

        if(runner == null) {
            runner = createRunner(consoleAdapter, promptProvider, commandExecutor, replashExceptionHandler);
        }

        // TODO Make this more configurable
        bannerEventListener.setStartupBannerEnabled(true);
        bannerEventListener.setShutdownBannerEnabled(true);
        runner.addEventListener(bannerEventListener);

        if(!ignoreBuiltinCommands) {
            withExitCommand();
            withHelpCommand();
        }

        ReplashRuntime runtime = createRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, runner, helpCommandHandler);

        for(ReplashEventListener eventListener : replashEventListeners) {
            runner.addEventListener(eventListener);
        }

        return new DefaultReplash(runtime, runner);
    }

    public ReplashBuilder withCommand(String commandName, BasicCommand basicCommand) {
        withCommand(commandName, new CommandTreeNode(basicCommand));
        return this;
    }

    public ReplashBuilder withCommands(Class<?> commandsClass) {
        List<ReflectionCommandResult> reflectionCommandResults = reflectionCommandFactory.create(commandsClass);
        for(ReflectionCommandResult result : reflectionCommandResults) {
            withCommand(result.getCommandName(), result.getNode());
        }
        return this;
    }

    public ReplashBuilder withCommands(Object commandsObject) {
        List<ReflectionCommandResult> reflectionCommandResults = reflectionCommandFactory.create(commandsObject);
        for(ReflectionCommandResult result : reflectionCommandResults) {
            withCommand(result.getCommandName(), result.getNode());
        }
        return this;
    }

    public ReplashBuilder withExitCommand() {
        ExitCommand exitCommand = new ExitCommand();
        return withCommand("exit", exitCommand).withCommand("quit", exitCommand);
    }

    public ReplashBuilder withHelpCommand() {
        HelpCommand helpCommand = new HelpCommand(helpCommandHandler);
        return withCommand("help", helpCommand);
    }

    public ReplashBuilder withoutBuiltinCommands() {
        ignoreBuiltinCommands = true;
        return this;
    }

    public ReplashBuilder withBanners() {
        return withStartupBanner().withShutdownBanner();
    }

    public ReplashBuilder withStartupBanner() {
        bannerEventListener.setStartupBannerEnabled(true);
        return this;
    }

    public ReplashBuilder withStartupBanner(String resourceName) {
        bannerEventListener.setStartupBannerResource(resourceName);
        return withStartupBanner();
    }

    public ReplashBuilder withShutdownBanner() {
        bannerEventListener.setShutdownBannerEnabled(true);
        return this;
    }

    public ReplashBuilder withShutdownBanner(String resourceName) {
        bannerEventListener.setShutdownBannerResource(resourceName);
        return withShutdownBanner();
    }

    public ReplashBuilder withoutBanners() {
        return withoutStartupBanner().withoutShutdownBanner();
    }

    public ReplashBuilder withoutStartupBanner() {
        bannerEventListener.setStartupBannerEnabled(false);
        return this;
    }

    public ReplashBuilder withoutShutdownBanner() {
        bannerEventListener.setShutdownBannerEnabled(false);
        return this;
    }

    public ReplashBuilder withPromptProvider(PromptProvider promptProvider) {
        this.promptProvider = promptProvider;
        return this;
    }

    protected ReplashBuilder withCommand(String commandName, CommandTreeNode node) {
        commandTree.addChild(commandName, node);
        return this;
    }

    public ReplashBuilder withEventListener(ReplashEventListener eventListener) {
        this.replashEventListeners.add(eventListener);
        return this;
    }

    protected ConsoleAdapter createConsole(ConsoleReader jlineConsoleReader) {
        return new ConsoleAdapterImpl(jlineConsoleReader);
    }

    protected void applyTabCompletionHandler(ConsoleAdapter consoleAdapter) {
        consoleAdapter.addCompleter(new DefaultTabCompletionHandler(commandTree));
    }

    protected void configureJLineConsoleReader(ConsoleReader jlineConsoleReader) {
        jlineConsoleReader.setHandleUserInterrupt(true);
    }

    protected CommandExecutor createCommandExecutor(CommandTextParser commandTextParser, CommandResolver commandResolver) {
        return new DefaultCommandExecutor(commandTextParser, commandResolver);
    }

    protected ReplashRuntime createRuntime(ConsoleAdapter consoleAdapter, CommandTextParser commandTextParser, CommandResolver commandResolver, CommandTree commandTree, ReplashRunner runner, HelpCommandHandler helpCommandHandler) {
        return new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, runner, helpCommandHandler);
    }

    protected ReplashRunner createRunner(ConsoleAdapter consoleAdapter, PromptProvider promptProvider, CommandExecutor commandExecutor, ReplashExceptionHandler replashExceptionHandler) {
        return new DefaultReplashRunner(consoleAdapter, promptProvider, commandExecutor, replashExceptionHandler);
    }
}
