package com.replash;

public class ReplashCommandExecutionException extends ReplashException {
    private final CommandContext executionContext;

    public ReplashCommandExecutionException(CommandContext executionContext, Exception exception) {
        super(createMessage(executionContext, exception), exception);
        this.executionContext = executionContext;
    }

    private static String createMessage(CommandContext executionContext, Exception exception) {
        return String.format("Replash command execution exception: %s", exception.getMessage());
    }

    public CommandContext getExecutionContext() {
        return executionContext;
    }
}
