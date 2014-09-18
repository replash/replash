package com.replash.help;

import com.replash.CommandContext;
import com.replash.ConsoleAdapter;
import com.replash.ReplashRuntime;
import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DefaultSimpleHelpHandler implements SimpleHelpHandler {
    @Override
    public void execute(CommandContext commandContext) throws IOException {
        ReplashRuntime runtime = commandContext.getRuntime();
        ConsoleAdapter consoleAdapter = runtime.getConsoleAdapter();
        CommandTree commandTree = runtime.getCommandTree();
        List<String> commandNames = new ArrayList<>();
        for(Map.Entry<String, CommandTreeNode> commandTreeNodeEntry : commandTree.getChildren()) {
            commandNames.add(commandTreeNodeEntry.getKey());
        }
        Collections.sort(commandNames);
        for(String commandName : commandNames) {
            commandContext.getRuntime().getConsoleAdapter().println(commandName);
        }
    }
}
