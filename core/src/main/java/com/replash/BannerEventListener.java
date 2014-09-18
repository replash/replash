package com.replash;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class BannerEventListener extends AbstractReplashEventListener {
    public static final String DEFAULT_STARTUP_BANNER_RESOURCE = "replash-startup.txt";
    public static final String DEFAULT_SHUTDOWN_BANNER_RESOURCE = "replash-shutdown.txt";
    private final ClassLoader classLoader;
    private boolean startupBannerEnabled;
    private boolean shutdownBannerEnabled;
    private String startupBannerResource = DEFAULT_STARTUP_BANNER_RESOURCE;
    private String shutdownBannerResource = DEFAULT_SHUTDOWN_BANNER_RESOURCE;

    public BannerEventListener() {
        this(BannerEventListener.class.getClassLoader());
    }

    public BannerEventListener(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean isStartupBannerEnabled() {
        return startupBannerEnabled;
    }

    public void setStartupBannerEnabled(boolean startupBannerEnabled) {
        this.startupBannerEnabled = startupBannerEnabled;
    }

    public boolean isShutdownBannerEnabled() {
        return shutdownBannerEnabled;
    }

    public void setShutdownBannerEnabled(boolean shutdownBannerEnabled) {
        this.shutdownBannerEnabled = shutdownBannerEnabled;
    }

    public String getStartupBannerResource() {
        return startupBannerResource;
    }

    public void setStartupBannerResource(String startupBannerResource) {
        this.startupBannerResource = startupBannerResource;
    }

    public String getShutdownBannerResource() {
        return shutdownBannerResource;
    }

    public void setShutdownBannerResource(String shutdownBannerResource) {
        this.shutdownBannerResource = shutdownBannerResource;
    }

    @Override
    public void beforeRun(ReplashRuntime runtime) {
        if(isStartupBannerEnabled()) {
            showStartupBanner(runtime);
        }
    }

    @Override
    public void beforeShutdown(ReplashRuntime runtime) {
        if(isShutdownBannerEnabled()) {
            showShutdownBanner(runtime);
        }
    }

    protected void showStartupBanner(ReplashRuntime runtime) {
        showBanner(startupBannerResource, runtime.getConsoleAdapter());
    }

    protected void showShutdownBanner(ReplashRuntime runtime) {
        showBanner(shutdownBannerResource, runtime.getConsoleAdapter());
    }

    protected void showBanner(String resourceName, ConsoleAdapter consoleAdapter) {
        try {
            String bannerText = readResource(resourceName);
            if(bannerText != null) {
                consoleAdapter.println(bannerText);
            }
        }
        catch (IOException e) {
            // Do nothing. A failure to display a banner isn't a critical error.
        }
    }

    protected String readResource(String resourceName) throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(resourceName);
        if(inputStream != null) {
            try {
                return IOUtils.toString(inputStream);
            }
            finally {
                inputStream.close();
            }
        }
        return null;
    }
}
