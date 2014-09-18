package com.replash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DefaultCommandTextParserTest {
    private ReplashRuntime runtime;
    private DefaultCommandTextParser commandTextParser;

    @Before
    public void setUp() throws Exception {
        runtime = mock(ReplashRuntime.class);
        commandTextParser = new DefaultCommandTextParser();
    }

    @Test(expected = CommandTextFormatException.class)
    public void parseWhenCommandTextIsNull() throws CommandTextFormatException {
        commandTextParser.parse(runtime, null);
    }

    @Test(expected = CommandTextFormatException.class)
    public void parseWhenCommandTextIsEmpty() throws CommandTextFormatException {
        commandTextParser.parse(runtime, "");
    }

    @Test(expected = CommandTextFormatException.class)
    public void parseWhenCommandTextIsWhitespace() throws CommandTextFormatException {
        commandTextParser.parse(runtime, "    ");
    }

    @Test
    public void parseWithCommandNameOnly() throws CommandTextFormatException {
        // Exercise
        CommandParameters commandParameters = commandTextParser.parse(runtime, "command");

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(0, commandParameters.getArguments().length);
    }

    @Test
    public void parseWithCommandNameAndOneArgument() throws CommandTextFormatException {
        // Exercise
        CommandParameters commandParameters = commandTextParser.parse(runtime, "command arg1");

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(1, commandParameters.getArguments().length);
        assertEquals("arg1", commandParameters.getArguments()[0]);
    }

    @Test
    public void parseWithCommandNameAndManyArguments() throws CommandTextFormatException {
        // Exercise
        CommandParameters commandParameters = commandTextParser.parse(runtime, "command arg1 arg2");

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(2, commandParameters.getArguments().length);
        assertEquals("arg1", commandParameters.getArguments()[0]);
        assertEquals("arg2", commandParameters.getArguments()[1]);
    }

    @Test
    public void parseWithCommandNameAndManyArgumentsAndExtraWhitespace() throws CommandTextFormatException {
        // Exercise
        CommandParameters commandParameters = commandTextParser.parse(runtime, "command   arg1   arg2  ");

        // Verify
        assertEquals("command", commandParameters.getCommandName());
        assertEquals(2, commandParameters.getArguments().length);
        assertEquals("arg1", commandParameters.getArguments()[0]);
        assertEquals("arg2", commandParameters.getArguments()[1]);
    }
}
