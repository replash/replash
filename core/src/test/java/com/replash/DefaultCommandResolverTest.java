package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DefaultCommandResolverTest {
    private static final BasicCommand DUMMY_BASIC_COMMAND = new DummyCommand();
    private DefaultCommandResolver commandResolver;
    private CommandTextParser commandTextParser;
    private ConsoleAdapter consoleAdapter;
    private PromptProvider promptProvider;
    private ReplashRunner replashRunner;

    @Before
    public void setUp() throws Exception {
        commandResolver = new DefaultCommandResolver();
        commandTextParser = mock(CommandTextParser.class);
        consoleAdapter = mock(ConsoleAdapter.class);
        promptProvider = mock(PromptProvider.class);
        replashRunner = mock(ReplashRunner.class);
    }

    @Test
    public void resolveWhenRootLevelCommandIsFound() {
        // Setup
        CommandTree commandTree = new CommandTree();
        commandTree.addChild("command", new CommandTreeNode(DUMMY_BASIC_COMMAND));
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, new DefaultHelpCommandHandler());
        CommandParameters commandParameters = new CommandParameters("command");

        // Execute
        CommandResolutionContext result = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNotNull(result);
        assertSame(DUMMY_BASIC_COMMAND, result.getNode().getBasicCommand());
        assertEquals("command", result.getCommandParameters().getCommandName());
        assertEquals(0, result.getCommandParameters().getArguments().length);
    }

    @Test
    public void resolveWhenRootLevelCommandIsNotFound() {
        // Setup
        CommandTree commandTree = new CommandTree();
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, new DefaultHelpCommandHandler());
        CommandParameters commandParameters = new CommandParameters("command");

        // Execute
        CommandResolutionContext result = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNull(result);
    }

    @Test
    public void resolveSimpleSubCommand() {
        // Setup
        Map<String, CommandTreeNode> children = new HashMap<>();
        children.put("sub", new CommandTreeNode(DUMMY_BASIC_COMMAND));
        CommandTree commandTree = new CommandTree();
        commandTree.addChild("parent", new CommandTreeNode(new ParentCommand(), children));
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, new DefaultHelpCommandHandler());
        CommandParameters commandParameters = new CommandParameters("parent", new String[]{"sub"});

        // Execute
        CommandResolutionContext result = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNotNull(result);
        assertSame(DUMMY_BASIC_COMMAND, result.getNode().getBasicCommand());
        assertEquals("sub", result.getCommandParameters().getCommandName());
        assertEquals(0, result.getCommandParameters().getArguments().length);
    }

    @Test
    public void resolveSubCommandWithArguments() {
        // Setup
        Map<String, CommandTreeNode> children = new HashMap<>();
        children.put("sub", new CommandTreeNode(DUMMY_BASIC_COMMAND));
        CommandTree commandTree = new CommandTree();
        commandTree.addChild("parent", new CommandTreeNode(new ParentCommand(), children));
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, new DefaultHelpCommandHandler());
        CommandParameters commandParameters = new CommandParameters("parent", new String[]{"sub", "arg1", "arg2"});

        // Execute
        CommandResolutionContext result = commandResolver.resolve(runtime, commandParameters);

        // Verify
        assertNotNull(result);
        assertSame(DUMMY_BASIC_COMMAND, result.getNode().getBasicCommand());
        assertEquals("sub", result.getCommandParameters().getCommandName());
        assertEquals(2, result.getCommandParameters().getArguments().length);
        assertEquals("arg1", result.getCommandParameters().getArguments()[0]);
        assertEquals("arg2", result.getCommandParameters().getArguments()[1]);
    }

    private static final class DummyCommand implements BasicCommand {
        @Override
        public void execute(CommandContext executionContext) {

        }
    }
}
