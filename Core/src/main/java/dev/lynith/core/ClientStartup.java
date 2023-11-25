package dev.lynith.core;

import dev.lynith.core.bridge.IVersion;

import java.lang.instrument.Instrumentation;

public class ClientStartup {

    public static void main(String[] args) {
        System.out.println("This shouldn't be run directly.");
        System.exit(1);
    }

    public static void launch(IVersion version) {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Hello from the client!");
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

}
