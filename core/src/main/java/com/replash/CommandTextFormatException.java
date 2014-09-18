package com.replash;

public class CommandTextFormatException extends ReplashException {
    private final String commandText;

    public CommandTextFormatException(String commandText, String reason) {
        super(createMessage(commandText, reason));
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }

    private static String createMessage(String commandText, String reason) {
        return String.format("Invalid command text format for command text \"%s\": %s", commandText, reason);
    }
}
