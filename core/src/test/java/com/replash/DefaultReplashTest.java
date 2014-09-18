package com.replash;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DefaultReplashTest {
    private ReplashRuntime runtime;
    private ReplashRunner runner;
    private DefaultReplash replash;

    @Before
    public void setUp() {
        runtime = mock(ReplashRuntime.class);
        runner = mock(ReplashRunner.class);
        replash = new DefaultReplash(runtime, runner);
    }

    @Test
    public void testRun() throws Exception {
        // Execute
        replash.run();

        // Verify
        verify(runner).run(runtime);
    }

    @Test
    public void testRunAsync() throws Exception {
        // Setup
        ExecutorService executorService = mock(ExecutorService.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable)invocation.getArguments()[0]).run();
                return null;
            }
        }).when(executorService).execute(any(Runnable.class));

        // Execute
        replash.runAsync(executorService);

        // Verify
        verify(runner).run(runtime);
    }
}