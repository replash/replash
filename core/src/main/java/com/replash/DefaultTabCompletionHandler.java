package com.replash;

import jline.console.completer.Completer;
import com.replash.commands.CommandTree;
import com.replash.commands.CommandTreeNode;

import java.util.List;
import java.util.Map;

public class DefaultTabCompletionHandler implements Completer {
    private final CommandTree commandTree;

    public DefaultTabCompletionHandler(CommandTree commandTree) {
        this.commandTree = commandTree;
    }

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        for(Map.Entry<String, CommandTreeNode> commandTreeNodeEntry : commandTree.getChildren()) {
            if(commandTreeNodeEntry.getKey().startsWith(buffer)) {
                candidates.add(commandTreeNodeEntry.getKey());
            }
        }
        return 0;
    }
}
