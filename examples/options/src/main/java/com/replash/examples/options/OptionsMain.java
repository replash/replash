package com.replash.examples.options;

import com.replash.*;
import com.replash.options.AbstractOptionsBasicCommand;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class OptionsMain {
    private static final class DateBasicCommand extends AbstractOptionsBasicCommand {
        public DateBasicCommand() {
            super(new Options().addOption(new Option("p", "pattern", true, "The date pattern")));
        }

        @Override
        protected void execute(CommandContext executionContext, ConsoleAdapter consoleAdapter, String commandName, Object[] arguments, Map<String, Object> options) throws Exception {
            String pattern = (String)options.get("pattern");

            if(StringUtils.isEmpty(pattern)) {
                Console.println(new Date().toString());
            }
            else {
                DateFormat dateFormat = new SimpleDateFormat(pattern);
                Console.println(dateFormat.format(new Date()));
            }
        }
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder()
                .withCommand("date", new DateBasicCommand())
                .build();
        replash.run();
    }
}
