package com.replash.examples.simple;

import com.replash.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleMain {
    private static final class DateCommand implements BasicCommand {
        @Override
        public void execute(CommandContext executionContext) throws Exception {
            Object[] arguments = executionContext.getCommandParameters().getArguments();
            if(arguments.length == 0) {
                Console.println(new Date().toString());
            }
            else if(arguments.length == 1) {
                String datePattern = arguments[0].toString();
                DateFormat dateFormat = new SimpleDateFormat(datePattern);
                Console.println(dateFormat.format(new Date()));
            }
            else {
                throw new ReplashCommandUsageException("Expected 0 or 1 argument");
            }
        }
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder().withCommand("date", new DateCommand()).build();
        replash.run();
    }
}
