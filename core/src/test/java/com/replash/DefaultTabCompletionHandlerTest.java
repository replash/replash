package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class DefaultTabCompletionHandlerTest {
    private CommandTree commandTree;
    private DefaultTabCompletionHandler tabCompletionHandler;

    @Before
    public void setUp() throws Exception {
        commandTree = new CommandTree();
        tabCompletionHandler = new DefaultTabCompletionHandler(commandTree);
    }

    @Test
    public void testCompleteWithEmptyTree() throws Exception {
        // Setup
        List<CharSequence> candidates = new ArrayList<>();

        // Execute
        int result = tabCompletionHandler.complete("abc", 0, candidates);

        // Verify
        assertEquals(0, result);
        assertEquals(0, candidates.size());
    }

    @Test
    public void testCompleteWithNonEmptyTreeNoneMatching() throws Exception {
        // Setup
        List<CharSequence> candidates = new ArrayList<>();
        commandTree.addChild("foo", new CommandTreeNode(mock(BasicCommand.class)));

        // Execute
        int result = tabCompletionHandler.complete("abc", 0, candidates);

        // Verify
        assertEquals(0, result);
        assertEquals(0, candidates.size());
    }

    @Test
    public void testCompleteWithNonEmptyTreeOneMatching() throws Exception {
        // Setup
        List<CharSequence> candidates = new ArrayList<>();
        commandTree.addChild("foo", new CommandTreeNode(mock(BasicCommand.class)));
        commandTree.addChild("abcdef", new CommandTreeNode(mock(BasicCommand.class)));

        // Execute
        int result = tabCompletionHandler.complete("abc", 0, candidates);

        // Verify
        assertEquals(0, result);
        assertEquals(1, candidates.size());
        assertEquals("abcdef", candidates.get(0));
    }

    @Test
    public void testCompleteWithNonEmptyTreeManyMatching() throws Exception {
        // Setup
        List<CharSequence> candidates = new ArrayList<>();
        commandTree.addChild("foo", new CommandTreeNode(mock(BasicCommand.class)));
        commandTree.addChild("abcdef", new CommandTreeNode(mock(BasicCommand.class)));
        commandTree.addChild("abcdefghijk", new CommandTreeNode(mock(BasicCommand.class)));

        // Execute
        int result = tabCompletionHandler.complete("abc", 0, candidates);

        // Verify
        assertEquals(0, result);
        assertEquals(2, candidates.size());
        assertThat(candidates, hasItem("abcdef"));
        assertThat(candidates, hasItem("abcdefghijk"));
    }
}