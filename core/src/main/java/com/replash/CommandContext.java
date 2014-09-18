package com.replash;

public class CommandContext {
    private final ReplashRuntime runtime;
    private final String commandText;
    private final CommandParameters commandParameters;
    private final BasicCommand command;

    public CommandContext(ReplashRuntime runtime, String commandText, CommandParameters commandParameters, BasicCommand command) {
        this.runtime = runtime;
        this.commandText = commandText;
        this.commandParameters = commandParameters;
        this.command = command;
    }

    public ReplashRuntime getRuntime() {
        return runtime;
    }

    public String getCommandText() {
        return commandText;
    }

    public CommandParameters getCommandParameters() {
        return commandParameters;
    }

    public BasicCommand getCommand() {
        return command;
    }
}
