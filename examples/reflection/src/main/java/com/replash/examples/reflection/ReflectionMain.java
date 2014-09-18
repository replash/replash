package com.replash.examples.reflection;

import com.replash.*;
import com.replash.ReplashBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReflectionMain {
    public static class DateCommand {
        @Command
        public void date1(CommandContext commandContext) throws IOException {
            displayDateWithPattern(commandContext, null);
        }

        @Command
        public void date2(CommandContext commandContext, String pattern) throws IOException {
            displayDateWithPattern(commandContext, pattern);
        }

        @Command
        public void date3(CommandContext commandContext, String pattern) throws IOException {
            displayDateWithPattern(commandContext, pattern);
        }

        @Command
        public void date4(CommandContext commandContext, @Argument String pattern) throws IOException {
            displayDateWithPattern(commandContext, pattern);
        }

        @Command
        public void date5(CommandContext commandContext, @Argument(required = false) String pattern) throws IOException {
            displayDateWithPattern(commandContext, pattern);
        }

        private void displayDateWithPattern(CommandContext commandContext, String pattern) throws IOException {
            ConsoleAdapter consoleAdapter = commandContext.getRuntime().getConsoleAdapter();
            if(pattern == null) {
                Console.println(new Date().toString());
            }
            else {
                DateFormat dateFormat = new SimpleDateFormat(pattern);
                Console.println(dateFormat.format(new Date()));
            }
        }
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder().withCommands(DateCommand.class).build();
        replash.run();
    }
}
