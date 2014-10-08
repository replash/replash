package com.replash;

import com.replash.commands.CommandTreeNode;

public interface HelpCommandHandler {
    void printHelp(CommandContext commandContext) throws Exception;
    void printHelp(CommandContext commandContext, CommandTreeNode commandTree) throws Exception;
    void printHelpWithContext(CommandContext commandContext) throws Exception;
}
