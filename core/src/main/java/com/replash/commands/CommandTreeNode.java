package com.replash.commands;

import com.replash.BasicCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandTreeNode {
    private CommandTreeNode parent;
    private final BasicCommand basicCommand;
    private final Map<String, CommandTreeNode> children;

    public CommandTreeNode(BasicCommand basicCommand) {
        this(basicCommand, new HashMap<String, CommandTreeNode>());
    }

    public CommandTreeNode(BasicCommand basicCommand, Map<String, CommandTreeNode> children) {
        this(null, basicCommand, children);
    }

    protected CommandTreeNode(CommandTreeNode parent, BasicCommand basicCommand, Map<String, CommandTreeNode> children) {
        this.parent = parent;
        this.basicCommand = basicCommand;
        this.children = children;
    }

    public CommandTreeNode getParent() {
        return parent;
    }

    private void setParent(CommandTreeNode parent) {
        this.parent = parent;
    }

    public BasicCommand getBasicCommand() {
        return basicCommand;
    }

    public Set<Map.Entry<String, CommandTreeNode>> getChildren() {
        return children.entrySet();
    }

    public CommandTreeNode getChild(String identifier) {
        return children.get(identifier);
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public void addChild(String identifier, CommandTreeNode childNode) {
        childNode.setParent(this);
        children.put(identifier, childNode);
    }
}
