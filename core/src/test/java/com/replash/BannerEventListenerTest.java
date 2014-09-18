package com.replash;

import org.junit.Test;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BannerEventListenerTest extends ReplashRuntimeBasedTest {
    private BannerEventListener eventListener;

    @Override
    public void setUp() {
        super.setUp();

        eventListener = new BannerEventListenerTss();
    }

    @Test
    public void beforeRunWhenStartupBannerIsEnabled() throws IOException {
        // Setup
        eventListener.setStartupBannerEnabled(true);

        // Execute
        eventListener.beforeRun(runtime);

        // Verify
        verify(consoleAdapter).println("~replash-startup.txt~");
    }

    @Test
    public void beforeRunWhenStartupBannerIsEnabledWithCustomResourceName() throws IOException {
        // Setup
        eventListener.setStartupBannerEnabled(true);
        eventListener.setStartupBannerResource("foo");

        // Execute
        eventListener.beforeRun(runtime);

        // Verify
        verify(consoleAdapter).println("~foo~");
    }

    @Test
    public void beforeShutdownWhenStartupBannerIsNotEnabled() throws IOException {
        // Setup
        eventListener.setShutdownBannerEnabled(false);

        // Execute
        eventListener.beforeShutdown(runtime);

        // Verify
        verify(consoleAdapter, never()).println(anyString());
    }

    @Test
    public void beforeShutdownWhenStartupBannerIsEnabled() throws IOException {
        // Setup
        eventListener.setShutdownBannerEnabled(true);

        // Execute
        eventListener.beforeShutdown(runtime);

        // Verify
        verify(consoleAdapter).println("~replash-shutdown.txt~");
    }

    @Test
    public void beforeShutdownWhenStartupBannerIsEnabledWithCustomResourceName() throws IOException {
        // Setup
        eventListener.setShutdownBannerEnabled(true);
        eventListener.setShutdownBannerResource("foo");

        // Execute
        eventListener.beforeShutdown(runtime);

        // Verify
        verify(consoleAdapter).println("~foo~");
    }

    @Test
    public void beforeRunWhenStartupBannerIsNotEnabled() throws IOException {
        // Setup
        eventListener.setStartupBannerEnabled(false);

        // Execute
        eventListener.beforeRun(runtime);

        // Verify
        verify(consoleAdapter, never()).println(anyString());
    }

    private static final class BannerEventListenerTss extends BannerEventListener {
        @Override
        protected String readResource(String resourceName) throws IOException {
            return String.format("~%s~", resourceName);
        }
    }
}