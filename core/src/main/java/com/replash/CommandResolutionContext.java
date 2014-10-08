package com.replash;

import com.replash.commands.CommandTreeNode;

public class CommandResolutionContext {
    private final CommandTreeNode node;
    private final CommandParameters commandParameters;

    public CommandResolutionContext(CommandTreeNode node, CommandParameters commandParameters) {
        this.node = node;
        this.commandParameters = commandParameters;
    }

    public CommandTreeNode getNode() {
        return node;
    }

    public CommandParameters getCommandParameters() {
        return commandParameters;
    }
}
