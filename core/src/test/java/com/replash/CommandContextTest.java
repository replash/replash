package com.replash;

import com.replash.commands.CommandTreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class CommandContextTest extends ReplashRuntimeBasedTest {
    @Test
    public void testConstructor() {
        // Setup
        CommandParameters commandParameters = new CommandParameters("command");
        BasicCommand command = mock(BasicCommand.class);
        CommandResolutionContext resolutionContext = new CommandResolutionContext(new CommandTreeNode(command), commandParameters);

        // Execute
        CommandContext commandContext = new CommandContext(runtime, "text", resolutionContext);

        // Verify
        assertSame(runtime, commandContext.getRuntime());
        assertEquals("text", commandContext.getCommandText());
        assertSame(resolutionContext, commandContext.getResolutionContext());
        assertSame(commandParameters, commandContext.getCommandParameters());
        assertSame(command, commandContext.getCommand());
    }
}