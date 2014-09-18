package com.replash;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class DefaultReplashExceptionHandler implements ReplashExceptionHandler {
    @Override
    public void handleException(ReplashRuntime runtime, ReplashException exception) {
        ConsoleAdapter consoleAdapter = runtime.getConsoleAdapter();

        try {
            if (exception instanceof ReplashCommandExecutionException) {
                CommandContext commandContext = ((ReplashCommandExecutionException) exception).getExecutionContext();
                Throwable innerThowable = exception.getCause();
                if (innerThowable instanceof ReplashException) {
                    handleReplashException(commandContext, (ReplashException) innerThowable, consoleAdapter);
                } else if (innerThowable instanceof ReplashCommandUsageException) {
                    handleReplashException(commandContext, (ReplashCommandUsageException) innerThowable, consoleAdapter);
                } else if (innerThowable instanceof ReplashRuntimeException) {
                    handleReplashException(commandContext, (ReplashRuntimeException) innerThowable, consoleAdapter);
                } else {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    innerThowable.printStackTrace(printWriter);

                    consoleAdapter.println(innerThowable.getMessage());
                    consoleAdapter.println(stringWriter.toString());
                }
            } else {
                handleReplashException(null, exception, consoleAdapter);
            }
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    protected void handleReplashException(CommandContext commandContext, ReplashException exception, ConsoleAdapter consoleAdapter) throws IOException {
        consoleAdapter.println(exception.getMessage());
    }

    protected void handleReplashException(CommandContext commandContext, ReplashCommandUsageException exception, ConsoleAdapter consoleAdapter) throws IOException {
        consoleAdapter.println(exception.getMessage());
        BasicCommand basicCommand = commandContext.getCommand();
        if(basicCommand instanceof CommandWithHelp) {
            ((CommandWithHelp) basicCommand).printHelp(commandContext, commandContext.getCommandParameters(), consoleAdapter);
        }
    }

    protected void handleReplashException(CommandContext commandContext, ReplashRuntimeException exception, ConsoleAdapter consoleAdapter) throws IOException {
        consoleAdapter.println(exception.getMessage());
    }
}
