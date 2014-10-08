package com.replash.examples.subcommands;

import com.replash.Command;
import com.replash.Console;
import com.replash.Replash;
import com.replash.ReplashBuilder;

public class SubCommandsMain {
    @Command(name = "basic")
    public void basicCommand() {
        Console.println("Hello World!");
    }

    @Command(name = "print")
    public static class PrintCommand {
        @Command(name = "name")
        public void nameCommand() {
            Console.println("Your name is Henry! Right?");
        }

        @Command(name = "my")
        public static class MyCommand {
            @Command(name = "name")
            public void nameCommand() {
                Console.println("Your name is Bob! I'm sure of it!");
            }
        }
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder()
                .withCommands(SubCommandsMain.class)
                .build();
        replash.run();
    }
}
