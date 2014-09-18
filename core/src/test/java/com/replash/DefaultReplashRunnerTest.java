package com.replash;

import com.replash.commands.CommandTreeNode;
import com.replash.commands.builtin.ExitCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class DefaultReplashRunnerTest extends ReplashRuntimeBasedTest {
    private DefaultReplashRunner runner;
    private CommandTextParser commandTextParser;
    private CommandResolver commandResolver;
    private CommandExecutor commandExecutor;
    private ReplashExceptionHandler exceptionHandler;
    private ReplashEventListener eventListener;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        promptProvider = new StaticTextPromptProvider("#");
        commandTextParser = new DefaultCommandTextParser();
        commandResolver = new DefaultCommandResolver();
        commandExecutor = new DefaultCommandExecutor(commandTextParser, commandResolver);
        exceptionHandler = mock(ReplashExceptionHandler.class);
        eventListener = mock(ReplashEventListener.class);
        runner = new DefaultReplashRunner(consoleAdapter, promptProvider, commandExecutor, exceptionHandler);
        runner.addEventListener(eventListener);
    }

    @Test
    public void testRunOnePassThroughRunLoop() throws Exception {
        // Setup
        commandTree.addChild("exit", new CommandTreeNode(new ExitCommand()));
        when(consoleAdapter.readLine("#")).thenReturn("exit");

        // Execute
        runner.run(runtime);

        // Verify
        InOrder inOrder = inOrder(consoleAdapter, eventListener);
        inOrder.verify(eventListener).beforeRun(runtime);
        inOrder.verify(eventListener).beforeCommand(runtime);
        inOrder.verify(consoleAdapter).readLine("#");
        inOrder.verify(eventListener).beforeExecuteCommand(runtime, "exit");
        inOrder.verify(eventListener).afterExecuteCommand(runtime, "exit");
        inOrder.verify(eventListener).afterCommand(runtime);
        inOrder.verify(eventListener).beforeCommand(runtime);
        inOrder.verify(eventListener).beforeShutdown(runtime);
        inOrder.verify(consoleAdapter).flush();
        inOrder.verify(consoleAdapter).shutdown();
        inOrder.verify(eventListener).afterShutdown(runtime);
    }

    @Test
    public void testRunManyPassesThroughRunLoop() throws Exception {
        // Setup
        commandTree.addChild("exit", new CommandTreeNode(new ExitCommand()));
        commandTree.addChild("dummy", new CommandTreeNode(mock(BasicCommand.class)));
        when(consoleAdapter.readLine("#")).thenReturn("dummy", "exit");

        // Execute
        runner.run(runtime);

        // Verify
        InOrder inOrder = inOrder(consoleAdapter, eventListener);
        inOrder.verify(eventListener).beforeRun(runtime);
        inOrder.verify(eventListener).beforeCommand(runtime);
        inOrder.verify(consoleAdapter).readLine("#");
        inOrder.verify(eventListener).beforeExecuteCommand(runtime, "dummy");
        inOrder.verify(eventListener).afterExecuteCommand(runtime, "dummy");
        inOrder.verify(eventListener).afterCommand(runtime);
        inOrder.verify(eventListener).beforeCommand(runtime);
        inOrder.verify(consoleAdapter).readLine("#");
        inOrder.verify(eventListener).beforeExecuteCommand(runtime, "exit");
        inOrder.verify(eventListener).afterExecuteCommand(runtime, "exit");
        inOrder.verify(eventListener).afterCommand(runtime);
        inOrder.verify(eventListener).beforeCommand(runtime);
        inOrder.verify(eventListener).beforeShutdown(runtime);
        inOrder.verify(consoleAdapter).flush();
        inOrder.verify(consoleAdapter).shutdown();
        inOrder.verify(eventListener).afterShutdown(runtime);
    }
}