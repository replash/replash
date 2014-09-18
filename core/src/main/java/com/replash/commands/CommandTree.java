package com.replash.commands;

import java.util.HashMap;

public class CommandTree extends CommandTreeNode {
    public CommandTree() {
        super(null, null, new HashMap<String, CommandTreeNode>());
    }
}
