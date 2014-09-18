package com.replash;

public class UnknownCommandException extends ReplashException {
    private final CommandParameters commandParameters;

    public UnknownCommandException(CommandParameters commandParameters) {
        super(createMessage(commandParameters));
        this.commandParameters = commandParameters;
    }

    public CommandParameters getCommandParameters() {
        return commandParameters;
    }

    private static String createMessage(CommandParameters commandParameters) {
        return String.format("Unknown command `%s`", commandParameters.getCommandName());
    }
}
