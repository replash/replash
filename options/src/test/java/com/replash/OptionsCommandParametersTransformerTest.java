package com.replash;

import com.replash.commands.CommandTree;
import com.replash.options.CommandParametersWithOptions;
import com.replash.options.OptionsBasicCommand;
import com.replash.options.OptionsCommandParametersTransformer;
import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class OptionsCommandParametersTransformerTest {
    private OptionsCommandParametersTransformer transformer;
    private PromptProvider promptProvider;
    private ReplashRunner replashRunner;
    private ReplashRuntime runtime;

    @Before
    public void setUp() {
        transformer = new OptionsCommandParametersTransformer(new BasicParser());
        promptProvider = mock(PromptProvider.class);
        replashRunner = mock(ReplashRunner.class);
        runtime = new ReplashRuntime(mock(ConsoleAdapter.class), new CommandTree(), promptProvider, replashRunner);
    }

    @Test
    public void transformSimpleCommand() throws ParseException {
        // Setup
        Options options = new Options();
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command";
        CommandParameters existingCommandParameters = new CommandParameters("command");
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(0, transformedCommandParameters.getArguments().length);
        assertEquals(0, transformedCommandParameters.getOptions().size());
    }

    @Test
    public void transformCommandWithOneArgument() throws ParseException {
        // Setup
        Options options = new Options();
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command arg1";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"arg1"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(1, transformedCommandParameters.getArguments().length);
        assertEquals("arg1", transformedCommandParameters.getArguments()[0]);
        assertEquals(0, transformedCommandParameters.getOptions().size());
    }

    @Test
    public void transformCommandWithManyArguments() throws ParseException {
        // Setup
        Options options = new Options();
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command arg1 arg2";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"arg1", "arg2"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(2, transformedCommandParameters.getArguments().length);
        assertEquals("arg1", transformedCommandParameters.getArguments()[0]);
        assertEquals("arg2", transformedCommandParameters.getArguments()[1]);
        assertEquals(0, transformedCommandParameters.getOptions().size());
    }

    @Test
    public void transformCommandWithOneShortOptionAndNoValue() throws ParseException {
        // Setup
        Options options = new Options();
        options.addOption(new Option("opt", "description"));
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command -opt";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"-opt"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(0, transformedCommandParameters.getArguments().length);
        assertEquals(1, transformedCommandParameters.getOptions().size());
        assertTrue(transformedCommandParameters.getOptions().containsKey("opt"));
    }

    @Test
    public void transformCommandWithOneShortOptionWithValue() throws ParseException {
        // Setup
        Options options = new Options();
        options.addOption(OptionBuilder.hasArg().create("opt"));
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command -opt value";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"-opt", "value"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(0, transformedCommandParameters.getArguments().length);
        assertEquals(1, transformedCommandParameters.getOptions().size());
        assertEquals("value", transformedCommandParameters.getOptions().get("opt"));
    }

    @Test
    public void transformCommandWithOneLongOptionAndNoValue() throws ParseException {
        // Setup
        Options options = new Options();
        options.addOption(new Option("o", "opt", false, "description"));
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command --opt";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"--opt"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(0, transformedCommandParameters.getArguments().length);
        assertEquals(1, transformedCommandParameters.getOptions().size());
        assertTrue(transformedCommandParameters.getOptions().containsKey("opt"));
    }

    @Test
    public void transformCommandWithOneLongOptionWithValue() throws ParseException {
        // Setup
        Options options = new Options();
        options.addOption(new Option("o", "opt", true, "description"));
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command --opt value";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"--opt", "value"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(0, transformedCommandParameters.getArguments().length);
        assertEquals(1, transformedCommandParameters.getOptions().size());
        assertEquals("value", transformedCommandParameters.getOptions().get("opt"));
    }

    @Test
    public void transformCommandOptionsAndArguments() throws ParseException {
        // Setup
        Options options = new Options();
        options.addOption(new Option("opt1", "description"));
        options.addOption(new Option("opt2", "description"));
        BasicCommand basicCommand = new StubBasicCommand(options);
        String commandText = "command -opt1 -opt2 arg1 arg2";
        CommandParameters existingCommandParameters = new CommandParameters("command", new String[]{"-opt1", "-opt2", "arg1", "arg2"});
        CommandContext existingCommandContext = new CommandContext(runtime, commandText, existingCommandParameters, basicCommand);

        // Exercise
        CommandParametersWithOptions transformedCommandParameters = transformer.transform(existingCommandContext, existingCommandParameters);

        // Verify
        assertEquals("command", transformedCommandParameters.getCommandName());
        assertEquals(2, transformedCommandParameters.getArguments().length);
        assertEquals("arg1", transformedCommandParameters.getArguments()[0]);
        assertEquals("arg2", transformedCommandParameters.getArguments()[1]);
        assertEquals(2, transformedCommandParameters.getOptions().size());
        assertTrue(transformedCommandParameters.getOptions().containsKey("opt1"));
        assertTrue(transformedCommandParameters.getOptions().containsKey("opt2"));
    }

    private static final class StubBasicCommand implements OptionsBasicCommand {
        private final Options options;

        private StubBasicCommand(Options options) {
            this.options = options;
        }

        @Override
        public void execute(CommandContext executionContext) {

        }

        @Override
        public Options getOptions() {
            return options;
        }

        @Override
        public void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException {

        }
    }
}
