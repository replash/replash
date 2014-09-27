package com.replash.examples.websamples;

import com.replash.Command;
import com.replash.Console;
import com.replash.Replash;
import com.replash.ReplashBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebSamplesMain {
    @Command(name = "date")
    public void printDate() {
        DateFormat dateFormat = new SimpleDateFormat();
        String dateStr = dateFormat.format(new Date());
        Console.println(dateStr);
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder()
                .withCommands(WebSamplesMain.class)
                .build();
        replash.run();
    }
}
