package com.replash;

import com.replash.commands.CommandTreeNode;

public class ParentCommand implements BasicCommand {
    @Override
    public void execute(CommandContext executionContext) throws Exception {
        CommandTreeNode node = executionContext.getResolutionContext().getNode();
        executionContext.getRuntime().getHelpCommandHandler().printHelp(executionContext, node);
    }
}
