package com.replash;

import com.replash.commands.CommandTreeNode;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DefaultHelpCommandHandler implements HelpCommandHandler {
    @Override
    public void printHelp(CommandContext commandContext) throws Exception {
        printHelp(commandContext, commandContext.getRuntime().getCommandTree());
    }

    @Override
    public void printHelpWithContext(CommandContext commandContext) throws Exception {
        CommandTextParser commandTextParser = commandContext.getRuntime().getCommandTextParser();
        CommandResolver commandResolver = commandContext.getRuntime().getCommandResolver();

        List<String> commandComponents = new ArrayList<>();
        for(Object arg : commandContext.getCommandParameters().getArguments()) {
            commandComponents.add(arg.toString());
        }
        String newCommandText = StringUtils.join(commandComponents, " ");

        CommandParameters commandParameters = commandTextParser.parse(commandContext.getRuntime(), newCommandText);
        CommandResolutionContext resolutionContext = commandResolver.resolve(commandContext.getRuntime(), commandParameters);
        if(resolutionContext == null) {
            throw new UnknownCommandException(commandParameters);
        }

        if(resolutionContext.getNode().isLeaf()) {
            ConsoleAdapter consoleAdapter = commandContext.getRuntime().getConsoleAdapter();
            BasicCommand basicCommand = resolutionContext.getNode().getBasicCommand();
            if(basicCommand instanceof CommandWithHelp) {
                ((CommandWithHelp) basicCommand).printHelp(commandContext, commandParameters, consoleAdapter);
            }
            else {
                Console.println("No help available for this command");
            }
        }
        else {
            printHelp(commandContext, resolutionContext.getNode());
        }
    }

    @Override
    public void printHelp(CommandContext commandContext, CommandTreeNode commandTree) throws Exception {
        ReplashRuntime runtime = commandContext.getRuntime();
        ConsoleAdapter consoleAdapter = runtime.getConsoleAdapter();

        List<Map.Entry<String, CommandTreeNode>> children = new ArrayList<>(commandTree.getChildren());
        Collections.sort(children, new CommandTreeNodeEntryComparator());

        for(Map.Entry<String, CommandTreeNode> entry : children) {
            consoleAdapter.println(entry.getKey());
        }
    }

    private static class CommandTreeNodeEntryComparator implements Comparator<Map.Entry<String, CommandTreeNode>> {
        @Override
        public int compare(Map.Entry<String, CommandTreeNode> o1, Map.Entry<String, CommandTreeNode> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    }
}
