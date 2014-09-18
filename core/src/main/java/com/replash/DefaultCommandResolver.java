package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;

public class DefaultCommandResolver implements CommandResolver {
    @Override
    public BasicCommand resolve(ReplashRuntime runtime, CommandParameters commandParameters) {
        return resolve(runtime.getCommandTree(), commandParameters);
    }

    public BasicCommand resolve(CommandTree commandTree, CommandParameters commandParameters) {
        CommandTreeNode child = commandTree.getChild(commandParameters.getCommandName());
        return child != null ? child.getBasicCommand() : null;
    }
}
