package com.replash.examples.basic;

import com.replash.Replash;
import com.replash.ReplashBuilder;

public class BasicMain {
    public static void main(String[] args) {
        Replash replash = new ReplashBuilder().build();
        replash.run();
    }
}
