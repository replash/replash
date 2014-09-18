package com.replash;

import com.replash.Console;
import com.replash.ConsoleAdapter;
import com.replash.ConsolePrintable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConsoleTest {
    private ConsoleAdapter consoleAdapter;

    @Before
    public void setUp() throws Exception {
        consoleAdapter = mock(ConsoleAdapter.class);
        Console.setConsoleAdapter(consoleAdapter);
    }

    @After
    public void tearDown() throws Exception {
        Console.setConsoleAdapter(null);
    }

    @Test
    public void testPrintWithString() throws Exception {
        // Execute
        Console.print("Foo");

        // Verify
        verify(consoleAdapter).print("Foo");
    }

    @Test
    public void testPrintWithPrintable() throws Exception {
        // Setup
        ConsolePrintable printable = mock(ConsolePrintable.class);

        // Execute
        Console.print(printable);

        // Verify
        verify(printable).print(consoleAdapter);
    }

    @Test
    public void testPrintlnWithString() throws Exception {
        // Execute
        Console.println("Foo");

        // Verify
        verify(consoleAdapter).println("Foo");
    }

    @Test
    public void testPrintlnWithPrintable() throws Exception {
        // Setup
        ConsolePrintable printable = mock(ConsolePrintable.class);

        // Execute
        Console.println(printable);

        // Verify
        InOrder inOrder = inOrder(printable, consoleAdapter);
        inOrder.verify(printable).print(consoleAdapter);
        inOrder.verify(consoleAdapter).println("");
    }

    @Test
    public void testPrintlnNoArgs() throws Exception {
        // Execute
        Console.println();

        // Verify
        verify(consoleAdapter).println("");
    }
}