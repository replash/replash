package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DefaultCommandResolverTest {
    private static final BasicCommand DUMMY_BASIC_COMMAND = new DummyCommand();
    private DefaultCommandResolver commandResolver;
    private ConsoleAdapter consoleAdapter;
    private PromptProvider promptProvider;
    private ReplashRunner replashRunner;

    @Before
    public void setUp() throws Exception {
        commandResolver = new DefaultCommandResolver();
        consoleAdapter = mock(ConsoleAdapter.class);
        promptProvider = mock(PromptProvider.class);
        replashRunner = mock(ReplashRunner.class);
    }

    @Test
    public void resolveWhenRootLevelCommandIsFound() {
        // Setup
        CommandTree commandTree = new CommandTree();
        commandTree.addChild("command", new CommandTreeNode(DUMMY_BASIC_COMMAND));
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTree, promptProvider, replashRunner);
        CommandParameters commandParameters = new CommandParameters("command");

        // Execute
        BasicCommand basicCommand = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNotNull(basicCommand);
        assertSame(DUMMY_BASIC_COMMAND, basicCommand);
    }

    @Test
    public void resolveWhenRootLevelCommandIsNotFound() {
        // Setup
        CommandTree commandTree = new CommandTree();
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTree, promptProvider, replashRunner);
        CommandParameters commandParameters = new CommandParameters("command");

        // Execute
        BasicCommand basicCommand = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNull(basicCommand);
    }

    private static final class DummyCommand implements BasicCommand {
        @Override
        public void execute(CommandContext executionContext) {

        }
    }
}
