package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DefaultReflectionCommandFactoryTest {
    private DefaultReflectionCommandFactory commandFactory;

    @Before
    public void setUp() {
        commandFactory = new DefaultReflectionCommandFactory();
    }

    @Test
    public void testCreateWithZeroCommandMethods() {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithZeroCommandMethods.class);

        // Verify
        assertEquals(0, results.size());
    }

    @Test
    public void testCreateWithOneCommandMethodWithZeroArguments() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithOneCommandMethodWithZeroArguments.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithOneCommandMethodWithZeroArguments.command1Executed);
    }

    @Test
    public void testCreateWithOneCommandMethodWithManyArguments() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithOneCommandMethodWithManyArguments.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode(), "arg1Value", "true");
        assertTrue(ClassWithOneCommandMethodWithManyArguments.command1Executed);
    }

    @Test
    public void testCreateWithManyCommands() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithManyCommands.class);

        // Verify
        assertEquals(2, results.size());
        ReflectionCommandResult result = findResultForCommand(results, "command1");
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithManyCommands.command1Executed);
        result = findResultForCommand(results, "command2");
        assertEquals("command2", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithManyCommands.command2Executed);
    }

    private static ReflectionCommandResult findResultForCommand(List<ReflectionCommandResult> results, String commandName) {
        for(ReflectionCommandResult result : results) {
            if(result.getCommandName().equals(commandName))
                return result;
        }
        fail();
        return null;
    }

    @Test
    public void testCreateWithTooFewArguments() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithTooFewArguments.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        try {
            executeCommand(result.getNode(), "arg1Value");
            fail();
        }
        catch(ReplashCommandUsageException e) {
            // Expected exception.
        }
        assertFalse(ClassWithTooFewArguments.command1Executed);
    }

    @Test
    public void testCreateWithTooManyArguments() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithTooManyArguments.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        try {
            executeCommand(result.getNode(), "arg1Value", "arg2Value", "arg3Value");
            fail();
        }
        catch(ReplashCommandUsageException e) {
            // Expected exception.
        }
        assertFalse(ClassWithTooManyArguments.command1Executed);
    }

    @Test
    public void testCreateWithOptionalArgumentsAndArgumentNotProvided() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithOptionalArguments.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithOptionalArguments.command1Executed);
    }

    @Test
    public void testCreateWithOptionalArgumentsAndArgumentProvided() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithOptionalArgumentsProvided.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode(), "value");
        assertTrue(ClassWithOptionalArgumentsProvided.command1Executed);
    }

    @Test
    public void testCreateWithCommandContextArgument() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithCommandContextArgument.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithCommandContextArgument.command1Executed);
    }

    @Test
    public void testCreateWithCommandWithCustomName() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithCustomName.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("customName", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithCustomName.command1Executed);
    }

    @Test
    public void testCreateWithCommandWithStaticMethod() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithStaticCommandMethod.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("command1", result.getCommandName());
        executeCommand(result.getNode());
        assertTrue(ClassWithStaticCommandMethod.command1Executed);
    }

    @Test
    public void testCreateWithSimpleSubCommand() throws Exception {
        // Execute
        List<ReflectionCommandResult> results = commandFactory.create(ClassWithSimpleSubCommand.class);

        // Verify
        assertEquals(1, results.size());
        ReflectionCommandResult result = results.get(0);
        assertEquals("sub", result.getCommandName());
        assertEquals(1, result.getNode().getChildren().size());
        executeCommand(result.getNode().getChildren().iterator().next().getValue());
        assertTrue(ClassWithSimpleSubCommand.SubCommand.command1Executed);
    }

    private void executeCommand(CommandTreeNode node, String... args) throws Exception {
        BasicCommand basicCommand = node.getBasicCommand();
        if(basicCommand instanceof ParentCommand) {
            fail("Not implemented for parent commands");
        }
        else {
            executeCommand((BasicCommand)basicCommand, args);
        }
    }

    private void executeCommand(BasicCommand basicCommand, String... args) throws Exception {
        executeCommand(basicCommand, new CommandParameters("commandName", args));
    }

    private void executeCommand(BasicCommand basicCommand, CommandParameters commandParameters) throws Exception {
        ConsoleAdapter consoleAdapter = mock(ConsoleAdapter.class);
        CommandTree commandTree = new CommandTree();
        commandTree.addChild("command", new CommandTreeNode(basicCommand));
        PromptProvider promptProvider = new StaticTextPromptProvider("> ");
        CommandExecutor commandExecutor = new DefaultCommandExecutor(new DefaultCommandTextParser(), new DefaultCommandResolver());
        ReplashExceptionHandler exceptionHandler = new DefaultReplashExceptionHandler();
        ReplashRunner replashRunner = new DefaultReplashRunner(consoleAdapter, promptProvider, commandExecutor, exceptionHandler);
        CommandTextParser commandTextParser = new DefaultCommandTextParser();
        CommandResolver commandResolver = new DefaultCommandResolver();
        ReplashRuntime runtime = new ReplashRuntime(consoleAdapter, commandTextParser, commandResolver, commandTree, promptProvider, replashRunner, new DefaultHelpCommandHandler());
        CommandResolutionContext resolutionContext = new CommandResolutionContext(new CommandTreeNode(basicCommand), commandParameters);
        CommandContext executionContext = new CommandContext(runtime, "commandText", resolutionContext);
        basicCommand.execute(executionContext);
    }

    public static class ClassWithZeroCommandMethods {
        public void thisIsNotACommandMethod() {

        }
    }

    public static class ClassWithOneCommandMethodWithZeroArguments {
        public static boolean command1Executed;

        @Command
        public void command1() {
            command1Executed = true;
        }
    }

    public static class ClassWithOneCommandMethodWithManyArguments {
        public static boolean command1Executed;

        @Command
        public void command1(String arg1, boolean arg2) {
            assertEquals("arg1Value", arg1);
            assertEquals(true, arg2);
            command1Executed = true;
        }
    }

    public static class ClassWithManyCommands {
        public static boolean command1Executed;
        public static boolean command2Executed;

        @Command
        public void command1() {
            command1Executed = true;
        }

        @Command
        public void command2() {
            command2Executed = true;
        }
    }

    public static class ClassWithTooFewArguments {
        public static boolean command1Executed;

        @Command
        public void command1(String arg1, String arg2) {
            command1Executed = true;
        }
    }

    public static class ClassWithTooManyArguments {
        public static boolean command1Executed;

        @Command
        public void command1(String arg1, String arg2) {
            command1Executed = true;
        }
    }

    public static class ClassWithCommandContextArgument {
        public static boolean command1Executed;

        @Command
        public void command1(CommandContext commandContext) {
            assertNotNull(commandContext);
            command1Executed = true;
        }
    }

    public static class ClassWithOptionalArguments {
        public static boolean command1Executed;

        @Command
        public void command1(@Argument(required = false)String arg1) {
            assertNull(arg1);
            command1Executed = true;
        }
    }

    public static class ClassWithOptionalArgumentsProvided {
        public static boolean command1Executed;

        @Command
        public void command1(@Argument(required = false)String arg1) {
            assertEquals("value", arg1);
            command1Executed = true;
        }
    }

    public static class ClassWithCustomName {
        public static boolean command1Executed;

        @Command(name = "customName")
        public void command1() {
            command1Executed = true;
        }
    }

    public static class ClassWithStaticCommandMethod {
        public static boolean command1Executed;

        @Command
        public static void command1() {
            command1Executed = true;
        }
    }

    public static class ClassWithSimpleSubCommand {
        @Command(name = "sub")
        public static class SubCommand {
            public static boolean command1Executed;

            @Command(name = "command")
            public void command1() {
                command1Executed = true;
            }
        }
    }
}
