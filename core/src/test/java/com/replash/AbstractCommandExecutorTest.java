package com.replash;

import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AbstractCommandExecutorTest extends ReplashRuntimeBasedTest {
    private CommandResolver commandResolver;
    private CommandTextParser commandTextParser;
    private AbstractCommandExecutorTss commandExecutor;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        commandResolver = new DefaultCommandResolver();
        commandTextParser = new DefaultCommandTextParser();
        commandExecutor = new AbstractCommandExecutorTss(commandResolver, commandTextParser);
    }

    @Test
    public void executeEmptyCommandText() throws ReplashException {
        // Execute
        commandExecutor.execute(runtime, "");

        // Verify
        assertNull(commandExecutor.executeCommandContext);
    }

    @Test
    public void executeResolvedCommand() throws ReplashException {
        // Verify
        BasicCommand command = mock(BasicCommand.class);
        commandTree.addChild("command", new CommandTreeNode(command));

        // Execute
        commandExecutor.execute(runtime, "command arg1 arg2");

        // Verify
        assertNotNull(commandExecutor.executeCommandContext);
        CommandParameters commandParameters = commandExecutor.executeCommandContext.getCommandParameters();
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(2, commandParameters.getArguments().length);
        assertEquals("arg1", commandParameters.getArguments()[0]);
        assertEquals("arg2", commandParameters.getArguments()[1]);
    }

    @Test(expected = UnknownCommandException.class)
    public void executeNonResolvedCommand() throws ReplashException {
        // Execute
        commandExecutor.execute(runtime, "command");
    }

    private static final class AbstractCommandExecutorTss extends AbstractCommandExecutor {
        public CommandContext executeCommandContext;

        public AbstractCommandExecutorTss(CommandResolver commandResolver, CommandTextParser commandTextParser) {
            super(commandResolver, commandTextParser);
        }

        @Override
        protected void executeCommand(CommandContext commandContext) throws ReplashCommandExecutionException {
            this.executeCommandContext = commandContext;
        }
    }
}