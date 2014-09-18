package com.replash;

import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DefaultCommandExecutorTest extends ReplashRuntimeBasedTest {
    private DefaultCommandExecutor commandExecutor;
    private CommandTextParser commandTextParser;
    private CommandResolver commandResolver;
    private Set<CommandExecutorPlugin> plugins;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        commandTextParser = new DefaultCommandTextParser();
        commandResolver = new DefaultCommandResolver();
        plugins = new HashSet<>();
        commandExecutor = new DefaultCommandExecutor(commandTextParser, commandResolver, plugins);
    }

    @Test
    public void testExecuteCommandWithoutPlugins() throws Exception {
        // Setup
        BasicCommand command = mock(BasicCommand.class);
        setupConsoleValidation(command);

        commandTree.addChild("command", new CommandTreeNode(command));
        String commandText = "command arg1 arg2";

        // Execute
        commandExecutor.execute(runtime, commandText);

        // Verify
        assertCommandAndText(command, commandText);
    }

    @Test
    public void testExecuteCommandWithNonMatchingPlugins() throws Exception {
        // Setup
        BasicCommand command = mock(BasicCommand.class);
        setupConsoleValidation(command);
        commandTree.addChild("command", new CommandTreeNode(command));
        String commandText = "command arg1 arg2";

        CommandExecutorPlugin plugin = mock(CommandExecutorPlugin.class);
        when(plugin.canExecute(command)).thenReturn(false);
        plugins.add(plugin);

        // Execute
        commandExecutor.execute(runtime, commandText);

        // Verify
        assertCommandAndText(command, commandText);
    }

    @Test
    public void testExecuteCommandWithMatchingPlugins() throws Exception {
        // Setup
        BasicCommand command = mock(BasicCommand.class);
        setupConsoleValidation(command);
        commandTree.addChild("command", new CommandTreeNode(command));
        String commandText = "command arg1 arg2";

        CommandExecutorPlugin plugin = mock(CommandExecutorPlugin.class);
        when(plugin.canExecute(command)).thenReturn(true);
        plugins.add(plugin);

        // Execute
        commandExecutor.execute(runtime, commandText);

        // Verify
        verify(command, never()).execute(any(CommandContext.class));
        verify(plugin).execute(any(CommandContext.class));
    }

    @Test(expected = AmbiguousCommandExecutorPluginException.class)
    public void testExecuteCommandWithMultipleMatchingPlugins() throws Exception {
        // Setup
        BasicCommand command = mock(BasicCommand.class);
        setupConsoleValidation(command);
        commandTree.addChild("command", new CommandTreeNode(command));
        String commandText = "command arg1 arg2";

        CommandExecutorPlugin plugin1 = mock(CommandExecutorPlugin.class);
        when(plugin1.canExecute(command)).thenReturn(true);
        plugins.add(plugin1);

        CommandExecutorPlugin plugin2 = mock(CommandExecutorPlugin.class);
        when(plugin2.canExecute(command)).thenReturn(true);
        plugins.add(plugin2);

        // Execute
        commandExecutor.execute(runtime, commandText);
    }

    private void setupConsoleValidation(BasicCommand command) throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                assertNotNull(Console.getConsoleAdapter());
                return null;
            }
        }).when(command).execute(any(CommandContext.class));
    }

    private void assertCommandAndText(BasicCommand command, String commandText) throws Exception {
        ArgumentCaptor<CommandContext> argumentCaptor = ArgumentCaptor.forClass(CommandContext.class);
        verify(command).execute(argumentCaptor.capture());
        CommandContext commandContext = argumentCaptor.getValue();
        assertSame(command, commandContext.getCommand());
        assertSame(runtime, commandContext.getRuntime());
        assertEquals(commandText, commandContext.getCommandText());
        assertEquals("command", commandContext.getCommandParameters().getCommandName());
        assertEquals("arg1", commandContext.getCommandParameters().getArguments()[0]);
        assertEquals("arg2", commandContext.getCommandParameters().getArguments()[1]);
    }
}