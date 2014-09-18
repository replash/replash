package com.replash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class StaticTextPromptProviderTest {
    private StaticTextPromptProvider promptProvider;

    @Before
    public void setUp() throws Exception {
        promptProvider = new StaticTextPromptProvider("<>");
    }

    @Test
    public void testGetPrompt() throws Exception {
        // Execute
        String prompt = promptProvider.getPrompt(mock(ReplashRuntime.class));

        // Verify
        assertEquals("<>", prompt);
    }
}