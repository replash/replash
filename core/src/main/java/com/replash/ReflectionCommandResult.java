package com.replash;

public class ReflectionCommandResult {
    private final String commandName;
    private final BasicCommand basicCommand;

    public ReflectionCommandResult(String commandName, BasicCommand basicCommand) {
        this.commandName = commandName;
        this.basicCommand = basicCommand;
    }

    public String getCommandName() {
        return commandName;
    }

    public BasicCommand getBasicCommand() {
        return basicCommand;
    }
}
