package com.replash;

import com.replash.commands.CommandTree;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created by cbeattie on 12/09/14.
 */
public class ReplashRuntimeBasedTest {
    protected ConsoleAdapter consoleAdapter;
    protected CommandTree commandTree;
    protected PromptProvider promptProvider;
    protected ReplashRunner replashRunner;
    protected ReplashRuntime runtime;

    @Before
    public void setUp() {
        consoleAdapter = mock(ConsoleAdapter.class);
        commandTree = new CommandTree();
        promptProvider = mock(PromptProvider.class);
        replashRunner = mock(ReplashRunner.class);

        runtime = new ReplashRuntime(consoleAdapter, commandTree, promptProvider, replashRunner);
    }
}
