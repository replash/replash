package com.replash;

import com.replash.commands.CommandTreeNode;

public class ReflectionCommandResult {
    private final String commandName;
    private final CommandTreeNode node;

    public ReflectionCommandResult(String commandName, CommandTreeNode node) {
        this.commandName = commandName;
        this.node = node;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandTreeNode getNode() {
        return node;
    }
}
