package com.replash;

import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;

import java.util.Arrays;

public class DefaultCommandResolver implements CommandResolver {
    @Override
    public CommandResolutionContext resolve(ReplashRuntime runtime, CommandParameters commandParameters) {
        return resolve(runtime.getCommandTree(), commandParameters);
    }

    public CommandResolutionContext resolve(CommandTree commandTree, CommandParameters commandParameters) {
        return resolve((CommandTreeNode) commandTree, commandParameters);
    }

    protected CommandResolutionContext resolve(CommandTreeNode commandTree, CommandParameters commandParameters) {
        CommandTreeNode child = commandTree.getChild(commandParameters.getCommandName());
        if(child != null) {
            if(child.isLeaf() || commandParameters.getArguments().length == 0) {
                return new CommandResolutionContext(child, commandParameters);
            }
            else {
                String commandName = commandParameters.getArguments()[0];
                String[] revisedArguments = Arrays.copyOfRange(commandParameters.getArguments(), 1, commandParameters.getArguments().length);
                CommandParameters revisedCommandParameters = new CommandParameters(commandName, revisedArguments);
                return resolve(child, revisedCommandParameters);
            }
        }
        return null;
    }
}
