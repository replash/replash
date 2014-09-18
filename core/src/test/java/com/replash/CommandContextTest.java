package com.replash;

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

        // Execute
        CommandContext commandContext = new CommandContext(runtime, "text", commandParameters, command);

        // Verify
        assertSame(runtime, commandContext.getRuntime());
        assertEquals("text", commandContext.getCommandText());
        assertSame(commandParameters, commandContext.getCommandParameters());
        assertSame(command, commandContext.getCommand());
    }
}