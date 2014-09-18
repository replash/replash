package com.replash;

import com.replash.CommandParameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandParametersTest {
    @Test
    public void testConstructorWithCommandName() {
        // Exercise
        CommandParameters commandParameters = new CommandParameters("command");

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(0, commandParameters.getArguments().length);
    }

    @Test
    public void testConstructorWithCommandNameAndArguments() {
        // Exercise
        CommandParameters commandParameters = new CommandParameters("command", new String[]{"arg1", "arg2"});

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(2, commandParameters.getArguments().length);
        assertEquals("arg1", commandParameters.getArguments()[0]);
        assertEquals("arg2", commandParameters.getArguments()[1]);
    }
}