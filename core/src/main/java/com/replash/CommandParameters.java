package com.replash;

public class CommandParameters {
    private final String commandName;
    private final String[] arguments;

    public CommandParameters(String command) {
        this(command, new String[]{});
    }

    public CommandParameters(String commandName, String[] arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }
}
