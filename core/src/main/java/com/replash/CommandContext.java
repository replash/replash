package com.replash;

public class CommandContext {
    private final ReplashRuntime runtime;
    private final String commandText;
    private final CommandResolutionContext resolutionContext;

    public CommandContext(ReplashRuntime runtime, String commandText, CommandResolutionContext resolutionContext) {
        this.runtime = runtime;
        this.commandText = commandText;
        this.resolutionContext = resolutionContext;
    }

    public ReplashRuntime getRuntime() {
        return runtime;
    }

    public String getCommandText() {
        return commandText;
    }

    public CommandResolutionContext getResolutionContext() {
        return resolutionContext;
    }

    public CommandParameters getCommandParameters() {
        return resolutionContext.getCommandParameters();
    }

    public BasicCommand getCommand() {
        return resolutionContext.getNode().getBasicCommand();
    }
}
