package com.replash;

import com.replash.commands.CommandTree;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class ReplashRuntimeBasedTest {
    protected ConsoleAdapter consoleAdapter;
    protected CommandTextParser commandTextParser;
    protected CommandResolver commandResolver;
    protected CommandTree commandTree;
    protected PromptProvider promptProvider;
    protected ReplashRunner replashRunner;
    protected ReplashRuntime runtime;
    protected HelpCommandHandler helpCommandHandler;

    @Before
    public void setUp() {
        consoleAdapter = mock(ConsoleAdapter.class);
        commandTextParser = mock(CommandTextParser.class);
        commandResolver = mock(CommandResolver.class);
        commandTree = new CommandTree();
        promptProvider = mock(PromptProvider.class);
        replashRunner = mock(ReplashRunner.class);

        runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, helpCommandHandler);
    }
}
